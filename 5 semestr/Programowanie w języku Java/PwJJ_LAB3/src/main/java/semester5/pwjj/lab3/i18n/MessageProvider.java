package semester5.pwjj.lab3.i18n;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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

	@NonNull
	private static final Map<Locale, ResourceBundle> LOADED_BUNDLES = new HashMap<>(3);

	/**
	 * Gets i18nized message.
	 *
	 * @param i18nProperty name of the i18n property to get
	 * @return message in current {@link Locale}
	 */
	@NonNull
	public static String get(final Messages.I18nProperty i18nProperty) {
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
	public static String get(final Messages.I18nProperty i18nProperty, @NonNull final Locale locale) {
		final String messageName = i18nProperty.propertyName();
		try {
			return getBundle(locale).getString(messageName);
		} catch (final MissingResourceException _) {
			log.warn("Translation for message <{}> for locale <{}> not found.", messageName, locale); //NON-NLS
			return String.format("<%s:%s>", locale, messageName); //NON-NLS
		}
	}

	@NonNull
	private static ResourceBundle getBundle(@NonNull final Locale locale) {
		synchronized (LOADED_BUNDLES) {
			return LOADED_BUNDLES.computeIfAbsent(locale, MessageProvider::loadBundle);
		}
	}

	@NonNull
	private static ResourceBundle loadBundle(@NonNull final Locale locale) {
		log.debug("Loading translations for the locale {}", locale); //NON-NLS
		return ResourceBundle.getBundle(I18N_PATH.toString(), locale);
	}
}
