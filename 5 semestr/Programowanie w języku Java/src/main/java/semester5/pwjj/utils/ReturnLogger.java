package semester5.pwjj.utils;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Interface providing methods for logging return value. */
@FunctionalInterface
public interface ReturnLogger {

	/** Cached loggers. FOR INTERNAL PURPOSES ONLY! */
	@NonNull
	Map<Class<?>, Logger> _CACHED_LOGGERS = new HashMap<>();

	/**
	 * Caches {@link Logger} and returns it.
	 * @param clazz class for which {@link Logger} is cached
	 * @return {@link Logger} for class {@code clazz}
	 */
	static @NonNull Logger getLogger(final @NonNull Class<?> clazz) {
		return _CACHED_LOGGERS.computeIfAbsent(clazz, LoggerFactory::getLogger);
	}

	/**
	 * Logs a method call and return its return value.
	 * @param returnValue  value to return
	 * @param callingClass class calling logger
	 * @param <T>          type of value to return
	 * @return {@code returnValue}
	 */
	static <T> @Nullable T trace(final @Nullable T returnValue, final @NonNull Class<?> callingClass) {
		return trace(returnValue, callingClass, null);
	}

	/**
	 * Logs a method call and return its return value; ensures the return value is not {@code null}.
	 * @param returnValue  value to return
	 * @param callingClass class calling logger
	 * @param <T>          type of value to return
	 * @return {@code returnValue}
	 */
	static <T> @NonNull T traceNotNull(final @NonNull T returnValue, final @NonNull Class<?> callingClass) {
		return Objects.requireNonNull(trace(returnValue, callingClass));
	}

	/**
	 * Logs a method call and return its return value.
	 * @param returnValue      value to return
	 * @param callingClass     class calling logger
	 * @param identityHashCode address of the caller object
	 * @param <T>              type of value to return
	 * @return {@code returnValue}
	 */
	private static <T> @Nullable T trace(final @Nullable T returnValue, final @NonNull Class<?> callingClass,
	                                     @Nullable final Integer identityHashCode) {
		@NonNull String methodName;
		try {
			final @NonNull StackTraceElement @NonNull [] stackTrace = Thread.currentThread().getStackTrace();
			methodName = Arrays.stream(stackTrace).skip(2)
				.dropWhile(it -> it.getMethodName().equals("trace") || it.getMethodName().equals("traceNotNull"))
				.toList().getFirst().getMethodName();
		} catch (final SecurityException _) {
			methodName = "<unknown>"; //NON-NLS
			getLogger(ReturnLogger.class)
				.trace("Unable to retrieve method name; falling back to {}", methodName); //NON-NLS
		}
		return trace(returnValue, callingClass, methodName, identityHashCode);
	}

	/**
	 * Logs a method call and return its return value.
	 * @param returnValue      value to return
	 * @param callingClass     class calling logger
	 * @param methodName       name of the called method
	 * @param identityHashCode address of the caller object
	 * @param <T>              type of value to return
	 * @return {@code returnValue}
	 */
	private static <T> @Nullable T trace(final @Nullable T returnValue, final @NonNull Class<?> callingClass,
	                                     final @Nullable String methodName, @Nullable final Integer identityHashCode) {
		@Nullable T returnValueCopy = returnValue;
		if (returnValueCopy instanceof final @NonNull String str) {
			returnValueCopy = (T) str.replace("\n", "\\n"); //NON-NLS
		}
		getLogger(callingClass).trace("{}@{}::{} -> {}",
			callingClass.getSimpleName(),
			Objects.requireNonNullElse(identityHashCode, "STATIC"), methodName, returnValueCopy);
		return returnValue;
	}

	/**
	 * Logs a method call and return its return value.
	 * @param returnValue value to return
	 * @param <T>         type of value to return
	 * @return {@code returnValue}
	 */
	default <T> @Nullable T trace(final @Nullable T returnValue) {
		return trace(returnValue, getClass(), System.identityHashCode(this));
	}

	/**
	 * Logs a method call and return its return value; ensures the return value is not {@code null}.
	 * @param returnValue value to return
	 * @param <T>         type of value to return
	 * @return {@code returnValue}
	 */
	default <T> @NonNull T traceNonNull(final @NonNull T returnValue) {
		return Objects.requireNonNull(trace(returnValue));
	}

	/** Logs constructor call based on object {@link ReturnLogger#_repr()} method. */
	default void traceCtor() {
		traceCtor(_repr());
	}

	/**
	 * Logs constructor call based on given {@code repr}.
	 * @param repr repr of instance
	 */
	default void traceCtor(final @NonNull String repr) {
		trace(repr, getClass(), "<ctor>", System.identityHashCode(this)); //NON-NLS
	}

	/**
	 * Creates {@link String} representation, FOR DEBBUGING PURPOSES ONLY.
	 * @return programmer friendly {@link String} representation
	 */
	@NonNull
	String _repr();
}
