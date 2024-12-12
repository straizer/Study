package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code utils.extensions} in the current locale.
 * @implNote If different translation is required without changing default locale,
 * use {@link MessageProvider#get(I18nProperty i18nProperty, Locale locale)}
 */
@ExtensionMethod({StringUtils.class, RepresentativeUtils.class})
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code utils.extensions.error} messages. */
	public enum Error {
		;

		/** I18n key with value {@code utils.extensions.error.exceptionInstantiationFailed}. */
		public static final @NonNull I18nProperty EXCEPTION_INSTANTIATION_FAILED =
			new UtilsExtensionsI18nProperty("error.exceptionInstantiationFailed");

		/** I18n key with value {@code utils.extensions.error.formatting}. */
		public static final @NonNull I18nProperty FORMATTING =
			new UtilsExtensionsI18nProperty("error.formatting");

		/**
		 * I18n value retriever for key {@code utils.extensions.error.exceptionInstantiationFailed}.
		 * @param clazz     related exception class
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.extensions.error.exceptionInstantiationFailed}
		 */
		public static @NonNull String EXCEPTION_INSTANTIATION_FAILED(
			final @NonNull Class<? extends Exception> clazz, final @NonNull Exception exception
		) {
			return EXCEPTION_INSTANTIATION_FAILED.getMessage().safeFormat(clazz.getSimpleName(), exception.getMessage())
				.traceNonNull(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.extensions.error.formatting}.
		 * @param template  template for which formatting failed
		 * @param args      args for which formatting failed
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.extensions.error.formatting}
		 */
		public static @NonNull String FORMATTING(
			final @NonNull String template, final @Nullable Object @Nullable [] args,
			final @NonNull IllegalFormatException exception
		) {
			return FORMATTING.getMessage().safeFormat(template, Arrays.toString(args), exception.getMessage())
				.traceNonNull(Error.class);
		}
	}


	/** Class storing i18n property constants for package {@code utils.extensions}. */
	public static class UtilsExtensionsI18nProperty extends I18nProperty {

		/**
		 * Creates an object of type {@code I18nProperty}.
		 * @param propertyName name of I18n property
		 */
		public UtilsExtensionsI18nProperty(@NonNull final String propertyName) {
			super("utils.extensions." + propertyName); //NON-NLS
		}
	}
}
