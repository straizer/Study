package semester5.pwjj.utils.i18n;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.utils.ReturnLogger;
import semester5.pwjj.utils.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/** I18n handler. */
@Slf4j
public enum MessageProvider {
	;

	private static final @NonNull Path I18N_PATH = Paths.get("i18n", "Messages");

	/**
	 * Gets i18nized message.
	 * @param i18nProperty name of the i18n property to get
	 * @return message in current {@link Locale}
	 */
	public static @NonNull String get(final @NonNull I18nProperty i18nProperty) {
		return get(i18nProperty, Locale.getDefault());
	}

	/**
	 * Gets i18nized message.
	 * @param i18nProperty name of the i18n property to get
	 * @param locale       locale to retrieve the message in
	 * @return message in current {@link Locale}
	 */
	public static @NonNull String get(final @NonNull I18nProperty i18nProperty, final @NonNull Locale locale) {
		final @NonNull String messageName = i18nProperty.getPropertyName();
		log.debug("Retrieving translation for locale <{}> for message <{}>", locale, messageName); //NON-NLS
		try {
			return ReturnLogger.traceNotNull(
				ResourceBundle.getBundle(I18N_PATH.toString(), locale).getString(messageName), MessageProvider.class);
		} catch (final MissingResourceException _) {
			log.warn("Translation for locale <{}> for message <{}> not found.", locale, messageName); //NON-NLS
			return ReturnLogger.traceNotNull(
				StringUtils.format("<%s:%s>", locale, messageName), MessageProvider.class); //NON-NLS
		}
	}
}
