package semester5.pwjj.utils.i18n;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Stream;

@DisplayName("Message Provider Tests")
final class MessageProviderTests {

	private static final @NonNull I18nProperty PROPERTY = I18nProperty.of("test.message");

	private static final @NonNull Locale POLISH_LOCALE = Locale.of("pl");
	private static final @NonNull Locale ENGLISH_LOCALE = Locale.ENGLISH;

	private static final @NonNull String POLISH_MESSAGE = "testowa wiadomość";
	private static final @NonNull String ENGLISH_MESSAGE = "test message";

	@BeforeAll
	static void beforeAll() throws NoSuchFieldException, IllegalAccessException {
		final @NonNull Field pathField = MessageProvider.class.getDeclaredField("I18N_PATH");
		pathField.setAccessible(true);
		final @NonNull Path path = (Path) pathField.get(null);
		ResourceBundle.clearCache();
		ResourceBundle.getBundle(path.toString(), POLISH_LOCALE, new ControlMock());
		ResourceBundle.getBundle(path.toString(), ENGLISH_LOCALE, new ControlMock());
	}

	@AfterAll
	static void afterAll() {
		ResourceBundle.clearCache();
	}

	private static @NonNull Stream<Arguments> getCustomTest() {
		return Stream.of(
			Arguments.of(POLISH_LOCALE, POLISH_MESSAGE),
			Arguments.of(ENGLISH_LOCALE, ENGLISH_MESSAGE));
	}

	@AfterEach
	void afterEach() {
		Locale.setDefault(Locale.ROOT);
	}

	@DisplayName("get default translation")
	@Test
	void getDefaultTest() {
		Locale.setDefault(ENGLISH_LOCALE);
		Assertions.assertThat(MessageProvider.get(PROPERTY)).isEqualTo(ENGLISH_MESSAGE);
	}

	@DisplayName("get custom translation")
	@ParameterizedTest(name = "[{index}] {0}")
	@MethodSource
	void getCustomTest(final @NonNull Locale locale, final @NonNull String expected) {
		Assertions.assertThat(MessageProvider.get(PROPERTY, locale)).isEqualTo(expected);
	}

	@DisplayName("non-existing translation language")
	@Test
	void nonExistingTranslationLanguageTest() {
		Locale.setDefault(Locale.FRENCH);
		Assertions.assertThat(MessageProvider.get(PROPERTY, Locale.getDefault()))
			.isEqualTo("<%s:%s>", Locale.getDefault(), PROPERTY.getPropertyName());
	}

	@DisplayName("non-existing translation property")
	@Test
	void nonExistingTranslationPropertyTest() {
		final @NonNull String propertyName = "test.missing";
		Assertions.assertThat(MessageProvider.get(I18nProperty.of(propertyName)))
			.isEqualTo("<%s:%s>", Locale.ROOT, propertyName);
	}

	private static class ResourceBundleMockBase extends ResourceBundle {

		private final @NonNull Map<String, String> map;

		ResourceBundleMockBase(final @NonNull String message) {
			map = Map.of(PROPERTY.getPropertyName(), message);
		}

		@Override
		protected void setParent(final @NonNull ResourceBundle parent) {
		}

		@Override
		protected @NonNull Object handleGetObject(final @NonNull String key) {
			return map.get(key);
		}

		@Override
		public @NonNull Enumeration<String> getKeys() {
			return Collections.enumeration(map.keySet());
		}
	}

	private static class ResourceBundleMockEnglish extends ResourceBundleMockBase {
		ResourceBundleMockEnglish() {
			super(ENGLISH_MESSAGE);
		}
	}

	private static class ResourceBundleMockPolish extends ResourceBundleMockBase {
		ResourceBundleMockPolish() {
			super(POLISH_MESSAGE);
		}
	}

	private static class ControlMock extends ResourceBundle.Control {

		private static final @NonNull ResourceBundle bundleEnglish = new ResourceBundleMockEnglish();
		private static final @NonNull ResourceBundle bundlePolish = new ResourceBundleMockPolish();

		@Override
		@Nullable
		public ResourceBundle newBundle(
			final @NonNull String baseName,
			final @NonNull Locale locale,
			final @NonNull String format,
			final @NonNull ClassLoader loader,
			final boolean reload) {
			if (locale.equals(POLISH_LOCALE)) {
				return bundlePolish;
			}
			if (locale.equals(ENGLISH_LOCALE)) {
				return bundleEnglish;
			}
			return null;
		}
	}
}
