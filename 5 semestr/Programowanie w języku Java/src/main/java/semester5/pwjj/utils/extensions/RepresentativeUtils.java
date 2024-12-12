package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import semester5.pwjj.Representative;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Utility class providing methods for tracing. */
@UtilityClass
@ExtensionMethod({Objects.class, ArrayUtils.class})
public class RepresentativeUtils {

	/** Cached loggers. FOR INTERNAL PURPOSES ONLY! */
	private final @NonNull Map<Class<?>, Logger> _CACHED_LOGGERS = new HashMap<>();

	/**
	 * Caches {@link Logger} and returns it.
	 * @param clazz class for which {@link Logger} is cached
	 * @return {@link Logger} for class {@code clazz}
	 */
	private @NonNull Logger getLogger(final @NonNull Class<?> clazz) {
		return _CACHED_LOGGERS.computeIfAbsent(clazz, LoggerFactory::getLogger);
	}

	/**
	 * Logs a method call and return its return value.
	 * @param returnValue  value to return
	 * @param callingClass class calling logger
	 * @param <T>          type of value to return
	 * @return {@code returnValue}
	 */
	public <T> @Nullable T trace(final @Nullable T returnValue, final @NonNull Class<?> callingClass) {
		return trace(returnValue, callingClass, null);
	}

	/**
	 * Logs a method call and return its return value; ensures the return value is not {@code null}.
	 * @param returnValue  value to return
	 * @param callingClass class calling logger
	 * @param <T>          type of value to return
	 * @return {@code returnValue}
	 */
	public <T> @NonNull T traceNonNull(final @NonNull T returnValue, final @NonNull Class<?> callingClass) {
		return trace(returnValue, callingClass).requireNonNull();
	}

	/**
	 * Logs a method call and return its return value.
	 * @param returnValue      value to return
	 * @param callingClass     class calling logger
	 * @param identityHashCode address of the caller object
	 * @param <T>              type of value to return
	 * @return {@code returnValue}
	 */
	public <T> @Nullable T trace(
		final @Nullable T returnValue, final @NonNull Class<?> callingClass, @Nullable final Integer identityHashCode
	) {
		@NonNull String methodName;
		try {
			final @NonNull StackTraceElement @NonNull [] stackTrace = Thread.currentThread().getStackTrace();
			methodName = stackTrace.skip(2)
				.dropWhile(it -> List.of("trace", "traceNonNull").contains(it.getMethodName())) //NON-NLS
				.toList().getFirst().getMethodName();
		} catch (final SecurityException _) {
			methodName = "<unknown>"; //NON-NLS
			getLogger(Representative.class)
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
	public <T> @Nullable T trace(
		final @Nullable T returnValue, final @NonNull Class<?> callingClass, final @NonNull String methodName,
		@Nullable final Integer identityHashCode
	) {
		final @Nullable Object returnValueCopy = switch (returnValue) {
			case final @NonNull String s -> s.replace("\n", "\\n"); //NON-NLS
			case final @NonNull Optional<?> o -> o.orElse(null); //NON-NLS
			case null, default -> returnValue;
		};
		getLogger(callingClass).trace("{}@{}::{} -> {}",
			callingClass.getSimpleName(), identityHashCode.requireNonNullElse("STATIC"), methodName,
			returnValueCopy);
		return returnValue;
	}
}
