package straizer.java.lang.object.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import straizer.java.lang.object.TestBase;
import straizer.java.lang.object.UUT;

final class GetClassTests extends TestBase {

	@Test
	@DisplayName("same as the Class object")
	void sameAsTheClassObjectTest() {
		Assertions.assertSame(UUT.class, uut.getClass());
	}

	@Test
	@DisplayName("getName equals full name")
	void getNameEqualsFullNameTest() {
		Assertions.assertEquals("straizer.java.lang.object.UUT", uut.getClass().getName());
	}
}
