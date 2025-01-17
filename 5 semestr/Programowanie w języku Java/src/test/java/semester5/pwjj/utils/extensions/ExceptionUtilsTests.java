package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@SuppressWarnings("TestMethodWithoutAssertion")
@DisplayName("Exception Utils Tests")
@ExtensionMethod(ExceptionUtils.class)
final class ExceptionUtilsTests extends UtilsExtensionsTestsBase {

	private static final String MESSAGE = "message";

	/** Method supplying {@link #warnAndReturnTest(Exception). */
	private static Stream<Arguments> warnAndReturnTest() {
		return Stream.of(
			Arguments.argumentSet("existing exception", MESSAGE.warnAndReturn(new Exception(MESSAGE))),
			Arguments.argumentSet("new exception", MESSAGE.warnAndReturn(Exception.class)));
	}

	@DisplayName("warn and return")
	@ParameterizedTest
	@MethodSource
	void warnAndReturnTest(final Exception exception) {
		Assertions.assertThat(exception).isInstanceOf(Exception.class).hasMessage(MESSAGE);
	}
}
