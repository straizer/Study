package semester5.pwjj.lab3;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * Base class for all error tests.
 */
public abstract class ErrorTestsBase extends TestsBase {

	protected static void throwsIllegalArgumentException(
		@NonNull final Locale locale,
		final ThrowableAssert.ThrowingCallable callable,
		@NonNull final Supplier<String> messageProvider) {
		Locale.setDefault(locale);
		Assertions.assertThatThrownBy(callable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage(messageProvider.get());
	}
}
