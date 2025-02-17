package semester5.pwjj.utils;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.utils.extensions.ExceptionUtils;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.extensions.TraceableUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/** Utility class for loading and accessing environment properties from a {@code .env} properties file. */
@SuppressWarnings("GrazieInspection")
@Slf4j
@UtilityClass
@ExtensionMethod({ExceptionUtils.class, TraceableUtils.class})
public class EnvProperties {

	/**
	 * The name of the {@code .env} properties file to be loaded.
	 * This file is expected to be present in the application's working directory.
	 */
	private final String FILENAME = ".env";

	/** A {@link Properties} object containing key-value pairs loaded from the {@code .env} properties file. */
	@SuppressWarnings("StaticCollection")
	private final Properties PROPERTIES = new Properties();

	static {
		log.debug("Loading properties from a file '{}'", FILENAME); //NON-NLS
		try (final FileInputStream stream = new FileInputStream(FILENAME)) {
			PROPERTIES.load(stream);
		} catch (final FileNotFoundException _) {
			log.warn(Messages.Error.NO_FILE_FOUND(FILENAME));
		} catch (final SecurityException _) {
			log.warn(Messages.Error.NO_READ_ACCESS(FILENAME));
		} catch (final IOException | IllegalArgumentException ex) {
			log.warn(Messages.Error.READING_FAILED(FILENAME, ex));
		}
		log.debug("Loaded properties from a file '{}':{}{}", //NON-NLS
			FILENAME, System.lineSeparator(), stringifyProperties());
	}

	/**
	 * Retrieves the value associated with the given {@code key} from the {@code .env} properties file.
	 * If the key isn't found, this method returns {@code null}.
	 * @param key the {@link String} key whose associated value is to be returned
	 * @return the value associated with {@code key}, or {@code null} if the key isn't found
	 */
	public @Nullable String get(final String key) {
		return PROPERTIES.getProperty(key).trace(EnvProperties.class, isKeyPassword(key));
	}

	/**
	 * Retrieves the value associated with the given {@code key} from the {@code .env} properties file.
	 * If the key isn't found, an exception of the specified type is thrown with the provided error message.
	 * @param key                  the {@link String} key whose associated value is to be returned
	 * @param errorMessageSupplier the error message supplier to be used if the key isn't found
	 * @param exceptionClass       the class of the exception to be thrown if the key isn't found
	 * @param <T>                  the type of the exception that may be thrown
	 * @return the value associated with {@code key}, if present
	 * @throws T if the key isn't found
	 */
	@SuppressWarnings("CheckedExceptionClass")
	public <T extends Exception> String get(
		final String key, final Supplier<String> errorMessageSupplier, final Class<T> exceptionClass
	) throws T {
		final @Nullable String value = get(key);
		if (Objects.isNull(value)) {
			throw errorMessageSupplier.get().warnAndReturn(exceptionClass);
		}
		return value;
	}

	/**
	 * Converts the loaded properties into a formatted {@link String}, with sensitive values obfuscated.
	 * Each key-value pair is represented as a string, and entries are joined by line separators.
	 * @return a {@link String} representation of the properties, with passwords obfuscated
	 */
	@SuppressWarnings({"argument", "return"})
	private String stringifyProperties() {
		return PROPERTIES.entrySet().stream().map(EnvProperties::obfuscatePasswords).map(Objects::toString)
			.collect(Collectors.joining(System.lineSeparator())).trace(EnvProperties.class);
	}

	/**
	 * Obfuscates the value of entries containing the substring {@code "password"} in their keys.
	 * This method ensures that sensitive information is masked for security purposes.
	 * @param entry the {@link Map.Entry} containing a key-value pair to be checked and obfuscated
	 * @return a new {@link Map.Entry} with the same key and an obfuscated value if the key contains {@code "password"};
	 * the original entry otherwise
	 */
	private Map.Entry<?, ?> obfuscatePasswords(final Map.Entry<Object, Object> entry) {
		final String key = (String) entry.getKey();
		return (isKeyPassword(key) ? Map.entry(key, StringUtils.obfuscate((CharSequence) entry.getValue())) : entry)
			.trace(EnvProperties.class);
	}

	/**
	 * Determines whether the given {@code key} represents a password.
	 * @param key the {@link String} key to be checked
	 * @return {@code true} if the key contains {@code password}, otherwise {@code false}
	 */
	private boolean isKeyPassword(final String key) {
		return key.toLowerCase(Locale.ROOT).contains("password"); //NON-NLS
	}
}
