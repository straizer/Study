package semester5.pwjj.utils;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.TestsBase;

import java.util.stream.Stream;

@DisplayName("Nullable Utils Tests")
final class StringUtilsTests extends TestsBase {

	private static @NonNull Stream<Arguments> formatTest() {
		return Stream.of(
			Arguments.argumentSet("invalid format", "%d", StringUtils.EMPTY, "%d"),
			Arguments.argumentSet("valid format", "%d", 0, "0"));
	}

	@DisplayName("format")
	@ParameterizedTest
	@MethodSource
	void formatTest(final @NonNull String template, final @NonNull Object args, final @NonNull String expected) {
		Assertions.assertThat(StringUtils.format(template, args)).isEqualTo(expected);
	}
}
