package semester5.pwjj.utils.extensions;

import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

import java.util.Optional;
import java.util.function.Function;

/**
 * Utility class providing methods to simplify working with {@link Nullable} objects and to streamline
 * interactions with functions that handle only {@link NonNull} input.
 */
@SuppressWarnings("ClassWithoutLogger")
@UtilityClass
public class NullableUtils {

	/**
	 * If the given {@code object} is not {@code null}, applies the provided {@code mapper} {@link Function}
	 * to it and returns the result; otherwise, returns {@code null}.
	 * @param object the input {@link Object} to be processed
	 * @param mapper the {@link Function} to apply to the input {@code object}, if it's not {@code null}
	 * @param <T>    the type of the input {@code object}
	 * @param <R>    the type of the result of the {@code mapper}
	 * @return a result of applying the {@code mapper} function to the input {@code object},
	 * or {@code null} if the input {@code object} is {@code null}
	 */
	public <@Nullable T, @Nullable R> @Nullable R mapOrNull(
		final @Nullable T object, final @NonNull Function<? super @NonNull T, ? extends @PolyNull R> mapper
	) {
		return Optional.ofNullable(object).map(mapper).orElse(null);
	}
}
