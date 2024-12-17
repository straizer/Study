package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.TestsBase;

import java.util.stream.Stream;

@SuppressWarnings("TestMethodWithoutAssertion")
@DisplayName("Exception Utils Tests")
@ExtensionMethod(ExceptionUtils.class)
final class ExceptionUtilsTests extends TestsBase {

	private static final @NonNull String MESSAGE = "message";

	/** Method supplying {@link #warnAndReturnTest(Exception). */
	private static @NonNull Stream<Arguments> warnAndReturnTest() {
		return Stream.of(
			Arguments.argumentSet("existing exception", MESSAGE.warnAndReturn(new Exception(MESSAGE))),
			Arguments.argumentSet("new exception", MESSAGE.warnAndReturn(Exception.class)));
	}

	@DisplayName("warn and return")
	@ParameterizedTest
	@MethodSource
	void warnAndReturnTest(final @NonNull Exception exception) {
		Assertions.assertThat(exception).isInstanceOf(Exception.class).hasMessage(MESSAGE);
	}
}
