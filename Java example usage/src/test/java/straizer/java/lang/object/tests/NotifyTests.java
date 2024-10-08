package straizer.java.lang.object.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import straizer.java.lang.object.NotifyTestBase;

final class NotifyTests extends NotifyTestBase {

	private static void verifySingleThreadIsTerminated(final Thread[] waiters) {
		boolean singleTerminated = false;
		for (final Thread waiter : waiters)
			try {
				Assertions.assertEquals(Thread.State.WAITING, waiter.getState());
			} catch (final AssertionError _) {
				if (singleTerminated)
					Assertions.fail("Multiple threads terminated.");
				else {
					Assertions.assertEquals(Thread.State.TERMINATED, waiter.getState());
					singleTerminated = true;
				}
			}
	}

	private static void stopThreads(final Thread[] threads) {
		for (final Thread thread : threads)
			thread.interrupt();
	}

	@Test
	@DisplayName("a single thread stopped after notification")
	void singleThreadStoppedAfterNotifyTest() throws InterruptedException {
		final Thread[] waiters = notifyPositiveCase(Object::notify);
		verifySingleThreadIsTerminated(waiters);
		stopThreads(waiters);
	}

	@Test
	@DisplayName(NOT_OWNER_TEST_NAME)
	void notOwnerOfMonitorTest() {
		Assertions.assertThrows(IllegalMonitorStateException.class, uut::notify);
	}
}
