package semester5.pwjj.entities;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import semester5.pwjj.TestsBase;

@DisplayName("Color Tests")
final class ColorTests extends TestsBase {

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

	@DisplayName("to pretty string")
	@Test
	void toPrettyStringTest() {
		Assertions.assertThat(Color.RED.toPrettyString()).isEqualTo(ENTITIES_TO_STRING_COLOR.getPropertyName());
		verifyMessageProviderMockWasUsedFor(ENTITIES_TO_STRING_COLOR);
	}
}
