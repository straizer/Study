package semester5.pwjj;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Abstract base class for handling error-related test assertions.
 * Provides methods to assert that specific exceptions are thrown with expected messages.
 */
@SuppressWarnings("AbstractClassWithOnlyOneDirectInheritor")
public abstract class ErrorTestsBase extends TestsBase {

	/**
	 * Asserts that the given {@code callable} throws an {@link IllegalArgumentException}
	 * with the specified error {@code message}.
	 * @param callable the {@link ThrowingCallable} to execute, which is expected to throw
	 *                 an {@link IllegalArgumentException}
	 * @param message  the expected error message of the thrown {@link IllegalArgumentException}
	 * @throws AssertionError if the {@link IllegalArgumentException} isn't thrown
	 *                        or if the message of the {@link IllegalArgumentException} isn't equal to {@code message}
	 */
	protected static void throwsIllegalArgumentException(
		final @NonNull ThrowingCallable callable, final @NonNull String message
	) {
		throwsException(callable, IllegalArgumentException.class, message);
	}

	/**
	 * Asserts that the given {@code callable} throws an {@link IllegalStateException}
	 * with the specified error {@code message}.
	 * @param callable the {@link ThrowingCallable} to execute, which is expected to throw
	 *                 an {@link IllegalStateException}
	 * @param message  the expected error message of the thrown {@link IllegalStateException}
	 * @throws AssertionError if the {@link IllegalStateException} isn't thrown
	 *                        or if the message of the {@link IllegalStateException} isn't equal to {@code message}
	 */
	protected static void throwsIllegalStateException(
		final @NonNull ThrowingCallable callable, final @NonNull String message
	) {
		throwsException(callable, IllegalStateException.class, message);
	}

	/**
	 * Asserts that the given {@code callable} throws the specified {@code exception}
	 * with the specified error {@code message}.
	 * @param callable  the {@link ThrowingCallable} to execute, which is expected to throw the {@code exception}
	 * @param exception the expected type of {@link Exception} that should be thrown
	 * @param message   the expected error message of the thrown {@code exception}
	 * @throws AssertionError if the {@code exception} isn't thrown
	 *                        or if the message of the {@code exception} isn't equal to {@code message}
	 */
	private static void throwsException(
		final @NonNull ThrowingCallable callable, final @NonNull Class<? extends Throwable> exception,
		final @NonNull String message
	) {
		Assertions.assertThatThrownBy(callable).isInstanceOf(exception).hasMessage(message);
	}
}
