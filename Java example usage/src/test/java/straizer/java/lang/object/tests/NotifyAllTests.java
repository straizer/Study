package straizer.java.lang.object.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import straizer.java.lang.object.NotifyTestBase;

final class NotifyAllTests extends NotifyTestBase {

	@Test
	@DisplayName("all threads stopped after notification")
	void allThreadsStoppedAfterNotifyTest() throws InterruptedException {
		final Thread[] waiters = notifyPositiveCase(Object::notifyAll);
		verifyThreads(waiters, Thread.State.TERMINATED);
	}

	@Test
	@DisplayName(NOT_OWNER_TEST_NAME)
	void notOwnerTest() {
		Assertions.assertThrows(IllegalMonitorStateException.class, uut::notifyAll);
	}
}
