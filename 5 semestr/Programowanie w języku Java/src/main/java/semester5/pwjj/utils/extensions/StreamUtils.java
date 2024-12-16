package semester5.pwjj.utils.extensions;

import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Utility class providing operations for working with {@link Stream}. */
@SuppressWarnings("ClassWithoutLogger")
@UtilityClass
public class StreamUtils {

	/**
	 * Performs a reduction operation on the elements of the provided {@code stream} of {@link String}, concatenating
	 * the elements using the specified {@code delimiter}, and adding the specified {@code prefix} and {@code suffix}.
	 * Elements are joined in encounter order, and this method is a terminal operation.
	 * @param stream    the {@link Stream} of {@link String} elements to be joined
	 * @param delimiter the delimiter to place between each element in the joined result
	 * @param prefix    the sequence of characters to prepend to the joined result
	 * @param suffix    the sequence of characters to append to the joined result
	 * @return a {@link String} resulting from the concatenation of the {@code stream} elements, separated by the given
	 * delimiter, and enclosed by the specified prefix and suffix
	 */
	public @NonNull String joining(
		final @NonNull Stream<@Nullable String> stream, final @NonNull CharSequence delimiter,
		final @NonNull CharSequence prefix, final @NonNull CharSequence suffix
	) {
		return stream.collect(Collectors.joining(delimiter, prefix, suffix));
	}

	/**
	 * Performs a reduction operation on the elements of the provided {@code stream} of {@link String}, concatenating
	 * the elements using the specified {@code delimiter}.
	 * The result is prefixed with {@code [} and suffixed with {@code ]}.
	 * Elements are joined in encounter order, and this method is a terminal operation.
	 * @param stream    the {@link Stream} of {@link String} elements to be joined
	 * @param delimiter the delimiter to be placed between each element in the joined result
	 * @return a {@link String} resulting from the concatenation of the {@code stream} elements, separated by the given
	 * delimiter, and enclosed by {@code [} and {@code ]}
	 */
	public @NonNull String joining(
		final @NonNull Stream<@Nullable String> stream, final @NonNull CharSequence delimiter
	) {
		return joining(stream, delimiter, "[", "]");
	}

	/**
	 * Retrieves the first element of the provided {@link Stream}, or {@code null} if the {@code stream} is empty.
	 * This is a terminal operation.
	 * @param stream the {@link Stream} from which the first element is to be retrieved
	 * @param <T>    the type of elements in the {@code stream}
	 * @return the first element of the provided {@code stream}, or {@code null} if the {@code stream} is empty
	 */
	public <@Nullable T> @PolyNull T getFirst(final @NonNull Stream<@PolyNull T> stream) {
		return stream.toList().getFirst();
	}
}
