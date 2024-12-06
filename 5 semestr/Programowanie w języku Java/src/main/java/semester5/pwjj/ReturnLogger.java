package semester5.pwjj;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Interface providing methods for logging return value.
 */
@FunctionalInterface
public interface ReturnLogger {

	/**
	 * Logs a method call and return its return value.
	 *
	 * @param returnValue      value to return
	 * @param callingClass     class calling logger
	 * @param identityHashCode address of the caller object
	 * @param <T>              type of value to return
	 * @return {@code returnValue}
	 */
	@SuppressWarnings({"unchecked", "HardcodedLineSeparator", "HardcodedFileSeparator", "CallToSuspiciousStringMethod"})
	@NonNull
	private static <T> T _log(@NonNull final T returnValue, @NonNull final Class<?> callingClass,
	                          @Nullable final Integer identityHashCode) {
		T returnValueCopy = returnValue;
		if (returnValueCopy instanceof final String str) {
			returnValueCopy = (T) str.replace("\n", "\\n"); //NON-NLS
		}
		final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		final String methodCandidate = stackTrace[2].getMethodName();
		LoggerFactory.getLogger(callingClass).trace("{}@{}::{} -> {}",
			callingClass.getSimpleName(),
			Objects.requireNonNullElse(identityHashCode, "STATIC"),
			methodCandidate.equals("_log") ? stackTrace[3].getMethodName() : methodCandidate,
			returnValueCopy);
		return returnValue;
	}

	/**
	 * Logs a method call and return its return value.
	 *
	 * @param returnValue  value to return
	 * @param callingClass class calling logger
	 * @param <T>          type of value to return
	 * @return {@code returnValue}
	 */
	@NonNull
	static <T> T _log(@NonNull final T returnValue, @NonNull final Class<?> callingClass) {
		return _log(returnValue, callingClass, null);
	}

	/**
	 * Logs a method call and return its return value.
	 *
	 * @param returnValue value to return
	 * @param <T>         type of value to return
	 * @return {@code returnValue}
	 */
	@NonNull
	default <T> T _log(@NonNull final T returnValue) {
		return _log(returnValue, getClass(), System.identityHashCode(this));
	}

	/**
	 * Logs constructor call based on given {@code repr}.
	 *
	 * @param repr repr of instance
	 */
	default void _logCtor(@NonNull final String repr) {
		LoggerFactory.getLogger(getClass())
			.trace("{}@{}::<ctor> -> {}", getClass().getSimpleName(), System.identityHashCode(this), repr); //NON-NLS
	}


	/**
	 * Logs constructor call based on object {@link ReturnLogger#_repr()} method.
	 */
	default void _logCtor() {
		_logCtor(_repr());
	}

	/**
	 * Creates {@link String} representation, FOR DEBBUGING PURPOSES ONLY.
	 *
	 * @return programmer friendly {@link String} representation
	 */
	@NonNull
	String _repr();
}
