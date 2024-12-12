package semester5.pwjj;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.checkerframework.checker.nullness.qual.NonNull;

/** Base class for all error tests. */
public abstract class ErrorTestsBase extends TestsBase {

	/**
	 * Asserts that calling {@code callable} is throwing {@link IllegalArgumentException} with {@code message}.
	 * @param callable callable to call
	 * @param message  error message
	 */
	protected static void throwsIllegalArgumentException(
		final @NonNull ThrowingCallable callable, final @NonNull String message
	) {
		throwsException(callable, IllegalArgumentException.class, message);
	}

	/**
	 * Asserts that calling {@code callable} is throwing {@link IllegalStateException} with {@code message}.
	 * @param callable callable to call
	 * @param message  error message
	 */
	protected static void throwsIllegalStateException(
		final @NonNull ThrowingCallable callable, final @NonNull String message
	) {
		throwsException(callable, IllegalStateException.class, message);
	}

	/**
	 * Asserts that calling {@code callable} is throwing {@code exception} with {@code message}.
	 * @param callable  callable to call
	 * @param exception exception to be thrown
	 * @param message   error message
	 */
	private static void throwsException(
		final @NonNull ThrowingCallable callable, final @NonNull Class<? extends Throwable> exception,
		final @NonNull String message
	) {
		Assertions.assertThatThrownBy(callable).isInstanceOf(exception).hasMessage(message);
	}
}
