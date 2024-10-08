package straizer.java.lang.object.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import straizer.java.lang.object.TestBase;

final class HashCodeTests extends TestBase {

	@Test
	@DisplayName("returns hash code")
	void returnHashCodeTest() {
		Assertions.assertEquals(DEFAULT_N, uut.hashCode());
	}
}
