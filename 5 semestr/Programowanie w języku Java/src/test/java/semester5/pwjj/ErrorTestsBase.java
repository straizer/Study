package semester5.pwjj;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Base class for all error tests.
 */
public abstract class ErrorTestsBase extends TestsBase {

	protected static void throwsIllegalArgumentException(
		final ThrowableAssert.ThrowingCallable callable,
		@NonNull final String message) {
		Assertions.assertThatThrownBy(callable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(message);
	}
}
