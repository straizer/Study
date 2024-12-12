package semester5.pwjj.utils.i18n;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.Arguments;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/** Base class for {@code utils.i18n} tests. */
abstract class I18nTestBase {

	protected static final @NonNull I18nProperty PROPERTY = I18nProperty.of("test.message");

	private static final @NonNull Locale POLISH_LOCALE = Locale.of("pl");
	private static final @NonNull Locale ENGLISH_LOCALE = Locale.ENGLISH;

	private static final @NonNull String POLISH_MESSAGE = "testowa wiadomość";
	private static final @NonNull String ENGLISH_MESSAGE = "test message";

	/** Method to execute before all tests in each extending class. */
	@BeforeAll
	static void beforeAll() throws NoSuchFieldException, IllegalAccessException {
		final @NonNull Field pathField = MessageProvider.class.getDeclaredField("I18N_PATH");
		pathField.setAccessible(true);
		final @NonNull Path path = (Path) pathField.get(null);
		ResourceBundle.clearCache();
		ResourceBundle.getBundle(path.toString(), POLISH_LOCALE, new ControlMock());
		ResourceBundle.getBundle(path.toString(), ENGLISH_LOCALE, new ControlMock());
	}

	/** Method to execute after all tests in each extending class. */
	@AfterAll
	static void afterAll() {
		ResourceBundle.clearCache();
	}

	/** Method supplying {@link org.junit.jupiter.params.ParameterizedTest} in each extending class. */
	private static @NonNull Stream<Arguments> getCustomTest() {
		return Stream.of(
			Arguments.of(POLISH_LOCALE, POLISH_MESSAGE),
			Arguments.of(ENGLISH_LOCALE, ENGLISH_MESSAGE));
	}

	/**
	 * Method testing that for default {@link Locale#ENGLISH} {@code function} when applied with {@code PROPERTY}
	 * is producing {@code ENGLISH_MESSAGE}.
	 * @param function function that's applied with {@code PROPERTY} and expected to produce {@code ENGLISH_MESSAGE}
	 */
	protected static void getDefaultTest(final @NonNull Function<? super I18nProperty, String> function) {
		Locale.setDefault(ENGLISH_LOCALE);
		Assertions.assertThat(function.apply(PROPERTY)).isEqualTo(ENGLISH_MESSAGE);
	}

	/**
	 * Method testing that {@code function} when applied with {@code PROPERTY} and {@code locale}
	 * is producing {@code expected}.
	 * @param function function that's applied with {@code PROPERTY} and {@code locale},
	 *                 and expected to produce {@code expected}
	 * @param locale   locale to apply to {@code function}
	 * @param expected expected return of {@code function} when applied with {@code PROPERTY} and {@code locale}
	 */
	protected static void getCustomTest(
		final @NonNull BiFunction<? super I18nProperty, ? super Locale, String> function,
		final @NonNull Locale locale, final @NonNull String expected
	) {
		Assertions.assertThat(function.apply(PROPERTY, locale)).isEqualTo(expected);
	}

	/**
	 * Method testing that for default {@link Locale#FRENCH} {@code function} when applied with {@code PROPERTY}
	 * is producing {@link String} indicating missing translation.
	 * @param function function that's applied with {@code PROPERTY}
	 *                 and expected to produce {@link String} indicating missing translation
	 */
	protected static void nonExistingTranslationLanguageTest(
		final @NonNull Function<? super I18nProperty, String> function
	) {
		Locale.setDefault(Locale.FRENCH);
		Assertions.assertThat(function.apply(PROPERTY))
			.isEqualTo("<%s:%s>", Locale.getDefault(), PROPERTY.getPropertyName());
	}

	/**
	 * Method testing that for default {@link Locale} {@code function} when applied with non-existing property
	 * is producing {@link String} indicating missing translation.
	 * @param function function that's applied with non-existing property
	 *                 and expected to produce {@link String} indicating missing translation
	 */
	protected static void nonExistingTranslationPropertyTest(
		final @NonNull Function<? super I18nProperty, String> function
	) {
		final @NonNull String propertyName = "test.missing";
		Assertions.assertThat(function.apply(I18nProperty.of(propertyName)))
			.isEqualTo("<%s:%s>", Locale.ROOT, propertyName);
	}

	/** Method to execute after each test in each extending class. */
	@AfterEach
	void afterEach() {
		Locale.setDefault(Locale.ROOT);
	}

	/** {@link ResourceBundle} mock base to be extended with language-specific mocks. */
	private static class ResourceBundleMockBase extends ResourceBundle {

		private final @NonNull Map<String, String> map;

		/**
		 * Creates {@link ResourceBundle} mock base, which for {@code PROPERTY} is returning {@code message}.
		 * @param message mocking message
		 */
		ResourceBundleMockBase(final @NonNull String message) {
			map = Map.of(PROPERTY.getPropertyName(), message);
		}

		/** Required to avoid infinite looping when looking for a parent. */
		@Override
		protected void setParent(final @NonNull ResourceBundle parent) {
		}

		/** Method returning mocked message. */
		@Override
		protected @NonNull Object handleGetObject(final @NonNull String key) {
			return map.get(key);
		}

		/** Method returning all translations. */
		@Override
		public @NonNull Enumeration<String> getKeys() {
			return Collections.enumeration(map.keySet());
		}
	}

	/** {@link ResourceBundle} mock for English language. */
	private static class ResourceBundleMockEnglish extends ResourceBundleMockBase {

		/**
		 * Creates {@link ResourceBundle} mock for English language,
		 * which for {@code PROPERTY} is returning {@code ENGLISH_MESSAGE}.
		 */
		ResourceBundleMockEnglish() {
			super(ENGLISH_MESSAGE);
		}
	}

	/** {@link ResourceBundle} mock for Polish language. */
	private static class ResourceBundleMockPolish extends ResourceBundleMockBase {

		/**
		 * Creates {@link ResourceBundle} mock for Polish language,
		 * which for {@code PROPERTY} is returning {@code POLISH_MESSAGE}.
		 */
		ResourceBundleMockPolish() {
			super(POLISH_MESSAGE);
		}
	}

	/** {@link ResourceBundle.Control} allowing populating {@link ResourceBundle} cache with language-specific mocks. */
	private static class ControlMock extends ResourceBundle.Control {

		private static final @NonNull ResourceBundle bundleEnglish = new ResourceBundleMockEnglish();
		private static final @NonNull ResourceBundle bundlePolish = new ResourceBundleMockPolish();

		/** Method for retrieving {@link ResourceBundle} language-specific mocks. */
		@Override
		public @Nullable ResourceBundle newBundle(
			final @NonNull String baseName, final @NonNull Locale locale, final @NonNull String format,
			final @NonNull ClassLoader loader, final boolean reload
		) {
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
