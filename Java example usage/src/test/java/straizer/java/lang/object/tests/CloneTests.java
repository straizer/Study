package straizer.java.lang.object.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import straizer.java.lang.object.TestBase;
import straizer.java.lang.object.UUT;
import straizer.java.lang.object.UUTNotCloneable;

final class CloneTests extends TestBase {

	@Test
	@DisplayName("clone not same instance")
	void cloneNotSameTest() throws CloneNotSupportedException {
		final UUT uutClone = uut.clone();
		Assertions.assertNotSame(uutClone, uut);
	}

	@Test
	@DisplayName("clone equals")
	void cloneEqualsTest() throws CloneNotSupportedException {
		final UUT uutClone = uut.clone();
		Assertions.assertEquals(uut, uutClone);
	}

	@Test
	@DisplayName("not Cloneable")
	void notCloneableTest() {
		final UUTNotCloneable uutNotCloneable = new UUTNotCloneable();
		Assertions.assertThrows(CloneNotSupportedException.class, uutNotCloneable::clone);
	}
}
