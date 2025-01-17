package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/** Utility class providing reflection-related operations and utilities. */
@Slf4j
@UtilityClass
@ExtensionMethod({ArrayUtils.class, StreamUtils.class})
public class ReflectionUtils {

	/**
	 * Retrieves the name of the method that called this method, skipping any method names specified in the provided
	 * {@code skippedNames} array. If the calling method's name can't be determined, the {@link StringUtils#UNKNOWN}
	 * is returned.
	 * @param skippedNames the array of method names to skip while determining the calling method's name
	 * @return a name of the calling method, or the {@link StringUtils#UNKNOWN} if retrieval fails
	 */
	public String getCallingMethodName(final String... skippedNames) {
		try {
			return Thread.currentThread().getStackTrace().skip(2)
				.dropWhile(it -> skippedNames.contains(it.getMethodName())).getFirst().getMethodName();
		} catch (final SecurityException _) {
			//noinspection DuplicateStringLiteralInspection
			log.trace("Unable to retrieve method name; falling back to {}", StringUtils.UNKNOWN); //NON-NLS
			return StringUtils.UNKNOWN;
		}
	}
}
