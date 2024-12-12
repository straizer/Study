package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Optional;
import java.util.function.Function;

/** Class containing utils for handling {@link Nullable} values. */
@UtilityClass
@ExtensionMethod(RepresentativeUtils.class)
public class NullableUtils {

	/**
	 * Applies {@code mapper} to {@code object} if {@code object} is not {@code null}.
	 * @param object {@link Nullable} object to map
	 * @param mapper mapper to apply
	 * @param <T>    type of {@code object}
	 * @param <R>    type of mapper return value
	 * @return {@code object} mapped by {@code mapper} to some type {@code <T>} if {@code object} is not {@code null};
	 * {@code null} otherwise
	 */
	public <T, R> @Nullable R mapOrNull(@Nullable final T object, final @NonNull Function<T, R> mapper) {
		return Optional.ofNullable(object).map(mapper).orElse(null).trace(NullableUtils.class);
	}
}
