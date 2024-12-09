package semester5.pwjj.utils.i18n;

import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.Representative;
import semester5.pwjj.utils.StringUtils;

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

		/** I18n key with value {@code utils.i18n.error.translationNotFound}. */
		public static final @NonNull I18nProperty TRANSLATION_NOT_FOUND = new UtilsI18nI18nProperty("error.translationNotFound");

		/**
		 * I18n value retriever for key {@code utils.i18n.error.translationNotFound}.
		 * @param locale      locale for which translation cannot be found
		 * @param messageName messageName for which translation cannot be found
		 * @return value of key {@code utils.i18n.error.translationNotFound}
		 */
		public static @NonNull String TRANSLATION_NOT_FOUND(
			final @NonNull Locale locale, final @NonNull String messageName) {
			return Representative.traceNonNull(
				StringUtils.format(MessageProvider.get(TRANSLATION_NOT_FOUND), locale, messageName), Error.class);
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
