package semester5.pwjj.entities;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Color Tests")
final class ColorTests extends EntitiesTestsBase {

	@DisplayName("red constant")
	@Test
	void redTest() {
		//noinspection NumericCastThatLosesPrecision
		Assertions.assertThat(Color.RED)
			.hasFieldOrPropertyWithValue("red", (byte) 255)
			.hasFieldOrPropertyWithValue("green", (byte) 0)
			.hasFieldOrPropertyWithValue("blue", (byte) 0)
			.hasFieldOrPropertyWithValue("alpha", (byte) 255);
	}

	@DisplayName("green constant")
	@Test
	void greenTest() {
		//noinspection NumericCastThatLosesPrecision
		Assertions.assertThat(Color.GREEN)
			.hasFieldOrPropertyWithValue("red", (byte) 0)
			.hasFieldOrPropertyWithValue("green", (byte) 255)
			.hasFieldOrPropertyWithValue("blue", (byte) 0)
			.hasFieldOrPropertyWithValue("alpha", (byte) 255);
	}

	@DisplayName("blue constant")
	@Test
	void blueTest() {
		//noinspection NumericCastThatLosesPrecision
		Assertions.assertThat(Color.BLUE)
			.hasFieldOrPropertyWithValue("red", (byte) 0)
			.hasFieldOrPropertyWithValue("green", (byte) 0)
			.hasFieldOrPropertyWithValue("blue", (byte) 255)
			.hasFieldOrPropertyWithValue("alpha", (byte) 255);
	}

	@DisplayName("to pretty string")
	@Test
	void toPrettyStringTest() {
		Assertions.assertThat(Color.RED.toPrettyString()).isEqualTo(TO_STRING_COLOR.getPropertyName());
		verifyMessageProviderMockWasUsedFor(TO_STRING_COLOR);
	}
}
