package semester5.pwjj.utils.extensions;

import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Class containing {@link Stream} utils. */
@UtilityClass
public class StreamUtils {

	/**
	 * Collects {@link Stream} of {@link String} using
	 * {@link Collectors#joining(CharSequence, CharSequence, CharSequence)}.
	 * @see Stream#collect(Collector)
	 * @see Collectors#joining(CharSequence, CharSequence, CharSequence)
	 */
	public @NonNull String joining(
		final @NonNull Stream<String> stream, final @NonNull CharSequence delimiter, final @NonNull CharSequence prefix,
		final @NonNull CharSequence suffix
	) {
		return stream.collect(Collectors.joining(delimiter, prefix, suffix));
	}

	/**
	 * Collects {@link Stream} of {@link String} using
	 * {@link Collectors#joining(CharSequence, CharSequence, CharSequence)},
	 * where prefix is {@code [} and suffix is {@code ]}.
	 * @see Stream#collect(Collector)
	 * @see Collectors#joining(CharSequence, CharSequence, CharSequence)
	 */
	public @NonNull String joining(final @NonNull Stream<String> stream, final @NonNull CharSequence delimiter) {
		return joining(stream, delimiter, "[", "]");
	}
}
