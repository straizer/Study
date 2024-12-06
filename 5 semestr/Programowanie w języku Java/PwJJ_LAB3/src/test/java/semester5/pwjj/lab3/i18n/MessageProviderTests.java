package semester5.pwjj.lab3.i18n;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.lab3.TestsBase;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Stream;

@DisplayName("Message Provider Tests")
class MessageProviderTests extends TestsBase {

	private static final MessageProvider.I18nProperty PROPERTY = new MessageProvider.I18nProperty("test.message");

	@NonNull
	private static final Locale POLISH_LOCALE = Locale.of("pl");
	@NonNull
	private static final Locale ENGLISH_LOCALE = Locale.ENGLISH;

	@NonNull
	private static final String POLISH_MESSAGE = "testowa wiadomość";
	@NonNull
	private static final String ENGLISH_MESSAGE = "test message";

	@BeforeAll
	static void beforeAll() throws NoSuchFieldException, IllegalAccessException {
		final Field pathField = MessageProvider.class.getDeclaredField("I18N_PATH");
		pathField.setAccessible(true);
		final Path path = (Path) pathField.get(null);
		ResourceBundle.clearCache();
		ResourceBundle.getBundle(path.toString(), POLISH_LOCALE, new ControlMock());
		ResourceBundle.getBundle(path.toString(), ENGLISH_LOCALE, new ControlMock());
	}

	@AfterAll
	static void afterAll() {
		ResourceBundle.clearCache();
	}

	@NonNull
	private static Stream<Arguments> getCustomTest() {
		return Stream.of(
			Arguments.of(POLISH_LOCALE, POLISH_MESSAGE),
			Arguments.of(ENGLISH_LOCALE, ENGLISH_MESSAGE));
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
	void getCustomTest(@NonNull final Locale locale, @NonNull final String expected) {
		Assertions.assertThat(MessageProvider.get(PROPERTY, locale)).isEqualTo(expected);
	}

	@DisplayName("non-existing translation language")
	@Test
	void nonExistingTranslationLanguageTest() {
		Locale.setDefault(Locale.FRENCH);
		Assertions.assertThat(MessageProvider.get(PROPERTY, Locale.getDefault()))
			.isEqualTo("<%s:%s>", Locale.getDefault(), PROPERTY.propertyName());
	}

	@DisplayName("non-existing translation property")
	@Test
	void nonExistingTranslationPropertyTest() {
		final String propertyName = "test.missing";
		Assertions.assertThat(MessageProvider.get(new MessageProvider.I18nProperty(propertyName)))
			.isEqualTo("<%s:%s>", Locale.ROOT, propertyName);
	}

	private static class ResourceBundleMockBase extends ResourceBundle {

		@NonNull
		private final Map<String, String> map;

		ResourceBundleMockBase(@NonNull final String message) {
			map = Map.of(PROPERTY.propertyName(), message);
		}

		@Override
		protected void setParent(@NonNull final ResourceBundle parent) {
		}

		@Override
		@NonNull
		protected Object handleGetObject(@NonNull final String key) {
			return map.get(key);
		}

		@Override
		@NonNull
		public Enumeration<String> getKeys() {
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

		@NonNull
		private static final ResourceBundle bundleEnglish = new ResourceBundleMockEnglish();
		@NonNull
		private static final ResourceBundle bundlePolish = new ResourceBundleMockPolish();

		@Override
		@Nullable
		public ResourceBundle newBundle(
			@NonNull final String baseName,
			@NonNull final Locale locale,
			@NonNull final String format,
			@NonNull final ClassLoader loader,
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
