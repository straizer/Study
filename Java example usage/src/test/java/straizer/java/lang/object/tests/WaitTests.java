package straizer.java.lang.object.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import straizer.java.lang.object.Interrupter;
import straizer.java.lang.object.Notifier;
import straizer.java.lang.object.NotifyWaitTestBase;

final class WaitTests extends NotifyWaitTestBase {

	private void synchronizedWait(final long timeoutMillis) throws InterruptedException {
		synchronized (uut) {
			uut.wait(timeoutMillis);
		}
	}

	private void synchronizedWait() throws InterruptedException {
		synchronizedWait(0L);
	}

	@Test
	@DisplayName("positive case")
	void positiveTest() throws InterruptedException {
		startThreads(1, new Notifier(uut));
		synchronizedWait();
	}

	@Test
	@DisplayName("not the owner")
	void notOwnerTest() {
		Assertions.assertThrows(IllegalMonitorStateException.class, uut::wait);
	}

	@Test
	@DisplayName("interrupted")
	void interruptedTest() {
		startThreads(1, new Interrupter(Thread.currentThread()));
		Assertions.assertThrows(InterruptedException.class, this::synchronizedWait);
	}

	@Test
	@DisplayName("timeout")
	void timeoutTest() throws InterruptedException {
		synchronizedWait(1L);
	}

	@Test
	@DisplayName("negative timeout")
	void negativeTimeoutTest() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> synchronizedWait(-1L));
	}
}
