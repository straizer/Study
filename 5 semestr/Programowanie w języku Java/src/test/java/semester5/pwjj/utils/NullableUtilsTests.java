package semester5.pwjj.utils;

import lombok.experimental.ExtensionMethod;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.TestsBase;

import java.util.stream.Stream;

@DisplayName("Nullable Utils Tests")
@ExtensionMethod(NullableUtils.class)
final class NullableUtilsTests extends TestsBase {

	private static @NonNull Stream<Arguments> mapOrNullTest() {
		return Stream.of(
			Arguments.argumentSet("null object", null, null),
			Arguments.argumentSet("non null object", StringUtils.EMPTY, 0));
	}

	@DisplayName("map or null")
	@ParameterizedTest
	@MethodSource
	void mapOrNullTest(final @Nullable String str, final @Nullable Integer expected) {
		Assertions.assertThat(str.mapOrNull(String::length)).isEqualTo(expected);
	}
}
