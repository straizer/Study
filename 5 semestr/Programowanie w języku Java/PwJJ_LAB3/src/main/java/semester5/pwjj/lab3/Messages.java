package semester5.pwjj.lab3;

import lombok.Setter;
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
public enum Messages {
	;

	private static final Path I18N_PATH = Paths.get("i18n", "Messages");
	private static final Map<Locale, ResourceBundle> LOADED_BUNDLES = new HashMap<>(3);

	@Setter
	private static Locale currentLocale = Locale.getDefault();

	/**
	 * Gets i18nized message.
	 *
	 * @param messageName name of the message to get
	 * @return message in current {@link Locale}
	 */
	public static String get(@NonNull final String messageName) {
		return get(messageName, currentLocale);
	}

	/**
	 * Gets i18nized message.
	 *
	 * @param messageName name of the message to get
	 * @param locale      locale to retrieve the message in
	 * @return message in current {@link Locale}
	 */
	public static String get(@NonNull final String messageName, @NonNull final Locale locale) {
		try {
			return getBundle(locale).getString(messageName);
		} catch (final MissingResourceException _) {
			log.warn("Translation for message <{}> for locale <{}> not found.", messageName, locale);
			return String.format("<%s:%s>", locale, messageName);
		}
	}

	/**
	 * Logs i18nized message with level {@code info}.
	 *
	 * @param messageName name of the message to log
	 * @param args        optional args for parameterized message
	 */
	public static void log(@NonNull final String messageName, @NonNull final Object @NonNull ... args) {
		log.info(get(messageName), args);
	}

	private static ResourceBundle getBundle(@NonNull final Locale locale) {
		synchronized (LOADED_BUNDLES) {
			return LOADED_BUNDLES.computeIfAbsent(locale, Messages::loadBundle);
		}
	}

	private static ResourceBundle loadBundle(@NonNull final Locale locale) {
		return ResourceBundle.getBundle(I18N_PATH.toString(), locale);
	}
}
