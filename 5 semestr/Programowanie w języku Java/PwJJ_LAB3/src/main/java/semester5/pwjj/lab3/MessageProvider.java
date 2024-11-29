package semester5.pwjj.lab3;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
public enum MessageProvider {
	;

	private static final Path I18N_PATH = Paths.get("i18n", "Messages");
	private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
	private static final Map<Locale, ResourceBundle> LOADED_BUNDLES = new HashMap<>(3);

	public static String get(@NonNull final String messageName) {
		return get(messageName, DEFAULT_LOCALE);
	}

	@SuppressWarnings("HardCodedStringLiteral")
	public static String get(@NonNull final String messageName, @NonNull final Locale locale) {
		try {
			return getBundle(locale).getString(messageName);
		} catch (final MissingResourceException _) {
			log.warn("Translation for message <{}> for locale <{}> not found.", messageName, locale);
			return String.format("<%s:%s>", locale, messageName);
		}
	}

	private static ResourceBundle getBundle(@NonNull final Locale locale) {
		synchronized (LOADED_BUNDLES) {
			return LOADED_BUNDLES.computeIfAbsent(locale, MessageProvider::loadBundle);
		}
	}

	private static ResourceBundle loadBundle(@NonNull final Locale locale) {
		return ResourceBundle.getBundle(I18N_PATH.toString(), locale);
	}
}
