package semester5.pwjj.utils.extensions;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.IllegalFormatException;

/** Class containing {@link String} utils. */
@Slf4j
@UtilityClass
public class StringUtils {

	/** Empty {@code String} literal. */
	public final String EMPTY = "";

	/**
	 * Wraps {@link String#formatted(Object...)} to never throw exception.
	 * @param template template to fill
	 * @param args     values to fill
	 * @return filled {@code template} if success; {@code template} otherwise
	 */
	public @NonNull String safeFormat(final @NonNull String template, final @Nullable Object @Nullable ... args) {
		try {
			return template.formatted(args);
		} catch (final IllegalFormatException ex) {
			log.warn(Messages.Error.FORMATTING(template, args, ex));
			return template;
		}
	}
}
