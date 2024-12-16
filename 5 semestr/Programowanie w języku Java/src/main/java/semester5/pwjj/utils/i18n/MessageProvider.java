package semester5.pwjj.utils.i18n;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.extensions.TraceableUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class to provide localized messages for a specified {@link I18nProperty} and {@link Locale}.
 * The class retrieves translations for messages defined in i18n resource bundles
 * located at a specified directory and base filename.
 */
@Slf4j
@UtilityClass
@ExtensionMethod({StringUtils.class, TraceableUtils.class, ResourceBundle.class})
public class MessageProvider {

	/**
	 * The constant {@link Path} to find the i18n resources like error or localized message definitions.
	 * This path is relative and specifies the directory and base filename for i18n resource files.
	 */
	private final @NonNull Path I18N_PATH = Paths.get("i18n", "Messages");

	/**
	 * Retrieves the translation for the given {@link I18nProperty} using the default {@link Locale}.
	 * If the translation can't be found, handles the {@link MissingResourceException}.
	 * @param i18nProperty the {@link I18nProperty} for which the translation is required
	 * @return a translated {@link String} for the given {@code i18nProperty} and default {@link Locale}
	 */
	public @NonNull String get(final @NonNull I18nProperty i18nProperty) {
		return get(i18nProperty, Locale.getDefault());
	}

	/**
	 * Retrieves the translation for the given {@code i18nProperty} and {@code locale}.
	 * If the translation can't be found, handles the {@link MissingResourceException}.
	 * @param i18nProperty the {@link I18nProperty} for which the translation is required
	 * @param locale       the {@link Locale} in which the translation is required
	 * @return a translated {@link String} for the given {@code i18nProperty} and {@code locale}
	 */
	public @NonNull String get(final @NonNull I18nProperty i18nProperty, final @NonNull Locale locale) {
		final @NonNull String messageName = i18nProperty.getPropertyName();
		log.debug("Retrieving translation for locale <{}> for message <{}>", locale, messageName); //NON-NLS
		try {
			return I18N_PATH.toString().getBundle(locale).getString(messageName).trace(MessageProvider.class);
		} catch (final MissingResourceException _) {
			return handleMissingResourceException(i18nProperty, locale);
		}
	}

	/**
	 * Logs at the WARN level the i18nized formatted message retrieved from property
	 * {@code utils.i18n.error.translationNotFound}.<br>
	 * Returns {@link String} indicating missing translation in form of {@code <{locale}:{i18nProperty}>}.
	 * If this translation can't be found, logs at the WARN level the non-i18nized formatted message in form of<br>
	 * {@code Translation for locale <{locale}> for message <utils.i18n.error.translationNotFound> not found.}.
	 * @param i18nProperty the {@link I18nProperty} representing the message key
	 * @param locale       the {@link Locale} for which the translation is missing
	 * @return a {@link String} in the format {@code <{locale}:{i18nProperty}>} representing the missing translation
	 */
	private @NonNull String handleMissingResourceException(
		final @NonNull I18nProperty i18nProperty, final @NonNull Locale locale
	) {
		final @NonNull String messageName = i18nProperty.getPropertyName();
		if (i18nProperty.equals(Messages.Error.TRANSLATION_NOT_FOUND)) {
			log.warn("Translation for locale <{}> for message <{}> not found.", locale, messageName); //NON-NLS
		} else {
			log.warn(Messages.Error.TRANSLATION_NOT_FOUND(locale, messageName));
		}
		return "<%s:%s>".safeFormat(locale, messageName).trace(MessageProvider.class); //NON-NLS
	}
}
