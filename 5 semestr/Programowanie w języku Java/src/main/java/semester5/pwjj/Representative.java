package semester5.pwjj;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.utils.extensions.RepresentativeUtils;

import java.util.Objects;

/** Interface providing methods for tracing. */
@ExtensionMethod({RepresentativeUtils.class, Objects.class})
public interface Representative {

	/**
	 * Logs a method call and return its return value.
	 * @param returnValue value to return
	 * @param <T>         type of value to return
	 * @return {@code returnValue}
	 */
	default <T> @Nullable T trace(final @Nullable T returnValue) {
		return returnValue.trace(getClass(), System.identityHashCode(this));
	}

	/**
	 * Logs a method call and return its return value; ensures the return value is not {@code null}.
	 * @param returnValue value to return
	 * @param <T>         type of value to return
	 * @return {@code returnValue}
	 */
	default <T> @NonNull T traceNonNull(final @NonNull T returnValue) {
		return trace(returnValue).requireNonNull();
	}

	/** Logs constructor call based on object {@link Object#toString()} method. */
	default void traceCtor() {
		RepresentativeUtils.trace(this, getClass(), "<ctor>", System.identityHashCode(this)); //NON-NLS
	}
}
