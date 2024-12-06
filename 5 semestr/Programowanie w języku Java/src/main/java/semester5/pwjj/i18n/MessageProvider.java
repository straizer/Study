package semester5.pwjj.i18n;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.ReturnLogger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * I18n handler.
 */
@Slf4j
public enum MessageProvider {
	;

	@NonNull
	private static final Path I18N_PATH = Paths.get("i18n", "Messages");

	/**
	 * Gets i18nized message.
	 *
	 * @param i18nProperty name of the i18n property to get
	 * @return message in current {@link Locale}
	 */
	@NonNull
	public static String get(final I18nProperty i18nProperty) {
		return get(i18nProperty, Locale.getDefault());
	}

	/**
	 * Gets i18nized message.
	 *
	 * @param i18nProperty name of the i18n property to get
	 * @param locale       locale to retrieve the message in
	 * @return message in current {@link Locale}
	 */
	@NonNull
	public static String get(final I18nProperty i18nProperty, @NonNull final Locale locale) {
		final String messageName = i18nProperty.propertyName();
		log.debug("Retrieving translation for locale <{}> for message <{}>", locale, messageName); //NON-NLS
		try {
			return ReturnLogger._log(
				ResourceBundle.getBundle(I18N_PATH.toString(), locale).getString(messageName), MessageProvider.class);
		} catch (final MissingResourceException e) {
			log.warn("Translation for locale <{}> for message <{}> not found.", locale, messageName); //NON-NLS
			return String.format("<%s:%s>", locale, messageName); //NON-NLS
		}
	}

	/**
	 * Class storing i18n property constants.
	 */
	public record I18nProperty(@NonNull String propertyName) {
	}
}
