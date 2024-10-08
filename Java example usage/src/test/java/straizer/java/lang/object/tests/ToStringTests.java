package straizer.java.lang.object.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import straizer.java.lang.object.TestBase;

final class ToStringTests extends TestBase {

	private static final String RESULT = "UUT[n=42]";

	@Test
	@DisplayName("toString")
	void toStringTest() {
		Assertions.assertEquals(RESULT, uut.toString());
	}

	@Test
	@DisplayName("String.valueOf")
	void stringValueOfTest() {
		Assertions.assertEquals(RESULT, String.valueOf(uut));
	}
}
