package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.IllegalFormatException;
import java.util.Objects;

/**
 * Utility class providing various helper methods and constants for common {@link String} operations.
 * This class includes predefined string constants and methods to handle {@link String} formatting safely,
 * ensuring robustness and reducing the risk of runtime exceptions.
 */
@Slf4j
@UtilityClass
@ExtensionMethod({ArrayUtils.class, Objects.class, NullableUtils.class})
public class StringUtils {

	/**
	 * A constant representing an empty {@link String} literal.
	 * Used as a placeholder to signify a blank or default value.
	 */
	public final String EMPTY = "";

	/**
	 * A constant representing a {@link String} literal value "{@code null}".
	 * Used as a placeholder or default value in scenarios where a {@code null} value needs to be explicitly
	 * represented as a {@link String}.
	 */
	public final String NULL = "null"; //NON-NLS

	/**
	 * A constant value representing the {@link String} literal "{@code <unknown>}".
	 * Used as a placeholder or default value in scenarios where the content is unknown or unspecified.
	 */
	public final String UNKNOWN = "<unknown>"; //NON-NLS

	/**
	 * Safely formats a {@link String} using the specified format {@link String} and arguments.
	 * If the provided arguments are {@code null}, the original format {@link String} is returned.
	 * If a formatting error occurs (for example, due to an invalid format {@link String} or mismatched arguments),
	 * the error is logged at the WARN level, and the original format {@link String} is returned.
	 * @param format the format {@link String} to be used
	 * @param args   the arguments referenced by the format specifiers in the format {@link String};
	 *               null arguments are replaced with {@link #NULL}.
	 *               If there are more arguments than format specifiers, the extra arguments are ignored.
	 * @return the formatted {@link String} if formatting succeeds; the original {@code format} otherwise
	 */
	@SuppressWarnings("argument")
	public String safeFormat(final String format, final @Nullable Object @Nullable ... args) {
		if (Objects.isNull(args) || args.length == 0) {
			return format;
		}
		try {
			return format.formatted(args.map(arg -> arg.requireNonNullElse(NULL)).toArray());
		} catch (final IllegalFormatException ex) {
			log.warn(Messages.Error.FORMATTING(format, args, ex));
			return format;
		}
	}

	/**
	 * Obfuscates the given {@code value} by replacing all characters with asterisks ({@code *}).
	 * The length of the returned {@link CharSequence} matches the length of the input, preserving its structure.
	 * @param value the {@link CharSequence} to be obfuscated
	 * @return a {@link CharSequence} of the same length as {@code value}, consisting only of asterisks ({@code *})
	 */
	public CharSequence obfuscate(final CharSequence value) {
		return "*".repeat(value.length());
	}
}
