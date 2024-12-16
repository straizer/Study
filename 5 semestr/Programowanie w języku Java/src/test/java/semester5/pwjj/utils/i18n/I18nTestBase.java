package semester5.pwjj.utils.i18n;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.util.NullnessUtil;
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
import java.util.ResourceBundle.Control;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/** Abstract base class for i18n test setups and utilities. */
@SuppressWarnings("ClassNamePrefixedWithPackageName")
abstract class I18nTestBase {

	/**
	 * Represents an instance of {@link I18nProperty} that encapsulates an i18n key used for retrieving
	 * internationalized messages.
	 * This property is a constant with the key "{@code test.message}".
	 */
	protected static final @NonNull I18nProperty PROPERTY = I18nProperty.of("test.message");

	/** Represents the Polish locale. */
	private static final @NonNull Locale POLISH_LOCALE = Locale.of("pl");

	/** Represents the English locale. */
	private static final @NonNull Locale ENGLISH_LOCALE = Locale.ENGLISH;

	/** A constant representing the Polish message used for testing purposes. */
	@SuppressWarnings("SpellCheckingInspection")
	private static final @NonNull String POLISH_MESSAGE = "testowa wiadomość";

	/** A constant representing the English message used for testing purposes. */
	private static final @NonNull String ENGLISH_MESSAGE = "test message";

	/**
	 * Method executed once before all tests in each extending class.
	 * The method ensures:
	 * <ol>
	 *     <li>Resource bundle caches are cleared before tests.</li>
	 *     <li>Mocked Polish and English resource bundles are loaded using {@link ControlMock}.</li>
	 * </ol>
	 * @throws NoSuchFieldException   if the `{@code I18N_PATH}` field isn't present in the {@link MessageProvider}
	 *                                class
	 * @throws IllegalAccessException if the field access attempt fails due to access restrictions
	 */
	@BeforeAll
	static void beforeAll() throws NoSuchFieldException, IllegalAccessException {
		final @NonNull Field pathField = MessageProvider.class.getDeclaredField("I18N_PATH");
		pathField.setAccessible(true);
		@SuppressWarnings("argument") final @NonNull Path path =
			NullnessUtil.castNonNull((@Nullable Path) pathField.get(null));
		ResourceBundle.clearCache();
		ResourceBundle.getBundle(path.toString(), POLISH_LOCALE, new ControlMock());
		ResourceBundle.getBundle(path.toString(), ENGLISH_LOCALE, new ControlMock());
	}

	/**
	 * Method executed once after all tests in each extending class.
	 * Clears the cache of {@link ResourceBundle} to ensure no residual data persists between tests.
	 */
	@AfterAll
	static void afterAll() {
		ResourceBundle.clearCache();
	}

	/**
	 * Provides test arguments for parameterized tests with a custom locale and an expected message.
	 * @return a {@link Stream} of {@link Arguments} where each argument consists of a locale
	 * and its corresponding expected message.
	 */
	@SuppressWarnings("unused")
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

	/**
	 * Method executed after each test in each extending class.
	 * Ensures the default locale is reset to {@link Locale#ROOT} to avoid
	 * any locale-specific side effects between tests.
	 */
	@AfterEach
	void afterEach() {
		Locale.setDefault(Locale.ROOT);
	}

	/**
	 * A base class extending {@link ResourceBundle} to mock translations for specific properties.
	 * This implementation provides a fixed mapping between a predefined property and its associated message.
	 * It can be extended to create mocks for different languages or locales.
	 */
	private static class ResourceBundleMockBase extends ResourceBundle {

		/** A mapping of property names to their corresponding mocked messages. */
		private final @NonNull Map<String, String> map;

		/**
		 * Constructs a {@code ResourceBundleMockBase} instance that maps the {@code PROPERTY} to the provided message.
		 * @param message the mocked message associated with the {@code PROPERTY}
		 */
		ResourceBundleMockBase(final @NonNull String message) {
			map = Map.of(PROPERTY.getPropertyName(), message);
		}

		/** This method is overridden to avoid infinite looping. */
		@Override
		protected void setParent(final @NonNull ResourceBundle parent) {
		}

		@SuppressWarnings("override.return")
		@Override
		protected @Nullable Object handleGetObject(final @NonNull String key) {
			return map.get(key);
		}

		@SuppressWarnings("argument")
		@Override
		public @NonNull Enumeration<String> getKeys() {
			return Collections.enumeration(map.keySet());
		}
	}

	/** A mock implementation of {@link ResourceBundle} specifically for the English language. */
	private static class ResourceBundleMockEnglish extends ResourceBundleMockBase {

		/**
		 * Constructs a {@code ResourceBundleMockEnglish} instance,
		 * which mocks a {@link ResourceBundle} for the English language.
		 */
		ResourceBundleMockEnglish() {
			super(ENGLISH_MESSAGE);
		}
	}

	/** A mock implementation of {@link ResourceBundle} specifically for the Polish language. */
	private static class ResourceBundleMockPolish extends ResourceBundleMockBase {

		/**
		 * Constructs a {@code ResourceBundleMockPolish} instance,
		 * which mocks a {@link ResourceBundle} for the Polish language.
		 */
		ResourceBundleMockPolish() {
			super(POLISH_MESSAGE);
		}
	}

	/** A mock implementation of {@link Control} that provides specific mock {@link ResourceBundle} instances. */
	private static class ControlMock extends Control {

		/** An English-specific mock {@link ResourceBundle} instance. */
		private static final @NonNull ResourceBundle bundleEnglish = new ResourceBundleMockEnglish();

		/** A Polish-specific mock {@link ResourceBundle} instance. */
		private static final @NonNull ResourceBundle bundlePolish = new ResourceBundleMockPolish();

		/**
		 * Retrieves a language-specific {@link ResourceBundle} based on the provided {@code locale}.
		 * @param baseName ignored
		 * @param locale   the specified {@link Locale} for which a resource bundle is desired
		 * @param format   ignored
		 * @param loader   ignored
		 * @param reload   ignored
		 * @return the corresponding {@link ResourceBundle} for the specified {@code locale} if available;
		 * {@code null} otherwise
		 */
		@SuppressWarnings("override.return")
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
