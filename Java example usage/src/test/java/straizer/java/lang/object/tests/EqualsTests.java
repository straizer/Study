package straizer.java.lang.object.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import straizer.java.lang.object.TestBase;
import straizer.java.lang.object.UUT;

final class EqualsTests extends TestBase {

	private static final int SECOND_N = 69;

	@Test
	@DisplayName("on self")
	void onSelfTest() {
		Assertions.assertTrue(uut.equals(uut));
	}

	@Test
	@DisplayName("on equal")
	void onEqualTest() {
		Assertions.assertTrue(uut.equals(new UUT(DEFAULT_N)));
	}

	@Test
	@DisplayName("on unequal")
	void onNotEqualTest() {
		Assertions.assertFalse(uut.equals(new UUT(SECOND_N)));
	}
}
