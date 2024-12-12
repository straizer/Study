package semester5.pwjj.utils.extensions;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;

/** Utility class providing methods for tracing. */
@Slf4j
@UtilityClass
public class ExceptionUtils {

	/**
	 * Logs {@code message} with level WARN and throws {@code exception} with that {@code message}.
	 * If exception instantiation fails, calls {@link System#exit(int)} with status code {@code 1}.
	 * @param message        message to log and supply exception with
	 * @param exceptionClass exception class to be thrown
	 * @param <T>            type of exception to be thrown
	 * @throws T supplied exception with {@code message}
	 */
	public <T extends Exception> void warnAndThrow(
		final @NonNull String message, final @NonNull Class<T> exceptionClass
	) throws T {
		log.warn(message); //NON-NLS
		T exception = null;
		try {
			exception = exceptionClass.getConstructor(String.class).newInstance(message);
		} catch (final ReflectiveOperationException ex) {
			log.error(Messages.Error.EXCEPTION_INSTANTIATION_FAILED(exceptionClass, ex));
			System.exit(1);
		}
		throw exception;
	}
}
