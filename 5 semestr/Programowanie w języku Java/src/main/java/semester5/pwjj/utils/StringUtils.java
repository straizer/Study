package semester5.pwjj.utils;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.utils.i18n.Messages;

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
			return ReturnLogger.traceNotNull(String.format(template, args), StringUtils.class);
		} catch (final IllegalFormatException ex) {
			log.warn(Messages.Error.FORMATTING(template, args, ex.getMessage()));
			return ReturnLogger.traceNotNull(template, StringUtils.class);
		}
	}
}
