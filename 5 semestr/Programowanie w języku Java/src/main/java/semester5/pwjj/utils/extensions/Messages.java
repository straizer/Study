package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code utils.extensions} in the current {@link Locale}.
 * @implNote If different translation is required without changing default {@link Locale},
 * use {@link MessageProvider#get(I18nProperty, Locale)}
 */
@SuppressWarnings({"PublicInnerClass", "StaticMethodOnlyUsedInOneClass"})
@ExtensionMethod({StringUtils.class, TraceableUtils.class})
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code utils.extensions.error} messages. */
	public enum Error {
		;

		/** I18n key with value {@code utils.extensions.error.exceptionInitializationFailed}. */
		public static final I18nProperty EXCEPTION_INITIALIZATION_FAILED =
			new UtilsExtensionsI18nProperty("error.exceptionInitializationFailed");

		/** I18n key with value {@code utils.extensions.error.formatting}. */
		public static final I18nProperty FORMATTING =
			new UtilsExtensionsI18nProperty("error.formatting");

		/**
		 * I18n value retriever for key {@code utils.extensions.error.exceptionInitializationFailed}.
		 * @param clazz     the related exception class
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.extensions.error.exceptionInitializationFailed}
		 */
		public static String EXCEPTION_INITIALIZATION_FAILED(
			final Class<? extends Exception> clazz, final Exception exception
		) {
			return EXCEPTION_INITIALIZATION_FAILED.getMessage()
				.safeFormat(clazz.getSimpleName(), exception.getMessage()).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.extensions.error.formatting}.
		 * @param template  the template for which formatting failed
		 * @param args      the args for which formatting failed
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.extensions.error.formatting}
		 */
		public static String FORMATTING(
			final String template, final @Nullable Object @Nullable [] args, final IllegalFormatException exception
		) {
			return FORMATTING.getMessage().safeFormat(template, Arrays.toString(args), exception.getMessage())
				.trace(Error.class);
		}
	}

	/**
	 * Utility class representing an i18n property specifically for the {@code utils.extensions} namespace.
	 * This class extends the {@link I18nProperty} class, allowing for the creation of specialized property keys by
	 * automatically prefixing them with "{@code utils.extensions.}".
	 */
	public static class UtilsExtensionsI18nProperty extends I18nProperty {

		/**
		 * Constructs an instance of {@code UtilsExtensionsI18nProperty} with a specific i18n property name.
		 * Prepends the property name with "{@code utils.extensions.}" to create the full property key.
		 * @param propertyName the name of the i18n property, which will be prefixed with "{@code utils.extensions.}"
		 */
		@SuppressWarnings("StringConcatenation")
		public UtilsExtensionsI18nProperty(final String propertyName) {
			super("utils.extensions." + propertyName); //NON-NLS
		}
	}
}
