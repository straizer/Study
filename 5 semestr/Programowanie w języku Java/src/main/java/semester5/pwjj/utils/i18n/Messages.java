package semester5.pwjj.utils.i18n;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.utils.ReturnLogger;
import semester5.pwjj.utils.StringUtils;

import java.util.Arrays;
import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code utils.i18n} in the current locale.
 * @implNote If different translation is required without changing default locale,
 * use {@link MessageProvider#get(I18nProperty i18nProperty, Locale locale)}
 */
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code utils.i18n.error} messages. */
	public enum Error {
		;

		/** I18n key with value {@code utils.i18n.error.formatting}. */
		public static final @NonNull I18nProperty FORMATTING = new UtilsI18nI18nProperty("error.formatting");

		/**
		 * I18n value retriever for key {@code utils.i18n.error.formatting}.
		 * @param template template for which formatting failed
		 * @param args     args for which formatting failed
		 * @param message  error message thrown by formatting call
		 * @return value of key {@code utils.i18n.error.formatting}
		 */
		public static @NonNull String FORMATTING(
			final @NonNull String template, final @Nullable Object @Nullable [] args, final @NonNull String message) {
			return ReturnLogger.traceNotNull(
				StringUtils.format(MessageProvider.get(FORMATTING), template, Arrays.toString(args), message),
				Error.class);
		}
	}


	/** Class storing i18n property constants for package {@code utils.i18n}. */
	public static class UtilsI18nI18nProperty extends I18nProperty {

		/**
		 * Creates an object of type {@code I18nProperty}.
		 * @param propertyName name of I18n property
		 */
		public UtilsI18nI18nProperty(@NonNull final String propertyName) {
			super("utils.i18n." + propertyName); //NON-NLS
		}
	}
}
