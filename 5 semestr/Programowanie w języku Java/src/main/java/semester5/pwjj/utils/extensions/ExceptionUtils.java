package semester5.pwjj.utils.extensions;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

/** Utility class providing methods for handling exceptions and logging messages. */
@Slf4j
@UtilityClass
public class ExceptionUtils {

	/**
	 * Logs the specified {@code message} at the WARN level and throws a new {@link Exception} of type {@link T}
	 * with the provided detail {@code message}.
	 * If the {@link Exception} initialization fails due to a {@link ReflectiveOperationException},
	 * logs the failure at the ERROR level and invokes {@link System#exit(int)} with a status code of {@code 1}.
	 * @param message        the detail message to log and use in the exception to be thrown;
	 *                       saved for retrieval by {@link Exception#getMessage()}.
	 * @param exceptionClass the {@link Class} of the {@link Exception} to be initialized and thrown.
	 * @param <T>            the type parameter representing the specific {@link Exception} type to be thrown.
	 * @throws T a newly created {@link Exception} of type {@link T} with the provided {@code message}
	 */
	@SuppressWarnings("CheckedExceptionClass")
	public <@NonNull T extends @NonNull Exception> void warnAndThrow(
		final @NonNull String message, final @NonNull Class<@NonNull T> exceptionClass
	) throws @NonNull T {
		@MonotonicNonNull T exception = null;
		try {
			exception = exceptionClass.getConstructor(String.class).newInstance(message);
		} catch (@SuppressWarnings("OverlyBroadCatchBlock") final ReflectiveOperationException ex) {
			log.error(Messages.Error.EXCEPTION_INITIALIZATION_FAILED(exceptionClass, ex));
			//noinspection CallToSystemExit
			System.exit(1);
		}
		warnAndThrow(message, exception);
	}

	/**
	 * Logs the provided {@code message} at the WARN level and throws the supplied {@code exception}.
	 * @param message   the message string to be logged
	 * @param exception the exception to be thrown
	 * @param <T>       the type of the {@code exception} to be thrown
	 * @throws T the supplied {@code exception}
	 */
	@SuppressWarnings("CheckedExceptionClass")
	public <@NonNull T extends @NonNull Exception> void warnAndThrow(
		final @NonNull String message, final @NonNull T exception
	) throws @NonNull T {
		log.warn(message);
		throw exception;
	}
}
