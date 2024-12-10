package semester5.pwjj.utils;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.IllegalFormatException;

/** Class containing {@link String} utils. */
@Slf4j
public enum StringUtils {
	;

	/** Empty {@code String} literal. */
	public static final String EMPTY = "";

	/**
	 * Wraps {@link String#format(String, Object...)} to never throw exception.
	 * @param template template to fill
	 * @param args     values to fill
	 * @return filled {@code template} if success; {@code template} otherwise
	 */
	public static @NonNull String format(final @NonNull String template, final @Nullable Object @Nullable ... args) {
		try {
			return String.format(template, args);
		} catch (final IllegalFormatException ex) {
			log.warn(Messages.Error.FORMATTING(template, args, ex));
			return template;
		}
	}
}
