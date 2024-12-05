package semester5.pwjj.lab3;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

@DisplayName("Color Tests")
class ColorTests extends TestsBase {

	@NonNull
	private static Stream<Arguments> toStringTest() {
		return Stream.of(
			Arguments.of(Locale.of("pl"), "czerwony: 0, zielony: 0, niebieski: 0, przezroczystość: 0"),
			Arguments.of(Locale.ENGLISH, "red: 0, green: 0, blue: 0, alpha: 0"));
	}

	@DisplayName("red constant")
	@Test
	void redTest() {
		Assertions.assertThat(Color.RED)
			.hasFieldOrPropertyWithValue("red", (byte) 255)
			.hasFieldOrPropertyWithValue("green", (byte) 0)
			.hasFieldOrPropertyWithValue("blue", (byte) 0)
			.hasFieldOrPropertyWithValue("alpha", (byte) 0);
	}

	@DisplayName("green constant")
	@Test
	void greenTest() {
		Assertions.assertThat(Color.GREEN)
			.hasFieldOrPropertyWithValue("red", (byte) 0)
			.hasFieldOrPropertyWithValue("green", (byte) 255)
			.hasFieldOrPropertyWithValue("blue", (byte) 0)
			.hasFieldOrPropertyWithValue("alpha", (byte) 0);
	}

	@DisplayName("blue constant")
	@Test
	void blueTest() {
		Assertions.assertThat(Color.BLUE)
			.hasFieldOrPropertyWithValue("red", (byte) 0)
			.hasFieldOrPropertyWithValue("green", (byte) 0)
			.hasFieldOrPropertyWithValue("blue", (byte) 255)
			.hasFieldOrPropertyWithValue("alpha", (byte) 0);
	}

	@DisplayName("to string")
	@ParameterizedTest(name = "[{index}] {0}")
	@MethodSource
	void toStringTest(@NonNull final Locale locale, @NonNull final String expected) {
		Locale.setDefault(locale);
		Assertions.assertThat(new Color((byte) 0, (byte) 0, (byte) 0).toString()).isEqualTo(expected);
	}
}
