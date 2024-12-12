package semester5.pwjj;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.checkerframework.checker.nullness.qual.NonNull;

/** Base class for all error tests. */
public abstract class ErrorTestsBase extends TestsBase {

	protected static void throwsIllegalArgumentException(
		final @NonNull ThrowingCallable callable, final @NonNull String message
	) {
		Assertions.assertThatThrownBy(callable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(message);
	}

	protected static void throwsIllegalStateException(
		final @NonNull ThrowingCallable callable, final @NonNull String message
	) {
		Assertions.assertThatThrownBy(callable)
			.isInstanceOf(IllegalStateException.class)
			.hasMessage(message);
	}
}
