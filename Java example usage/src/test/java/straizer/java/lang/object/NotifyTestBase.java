package straizer.java.lang.object;

import org.junit.jupiter.api.Assertions;

import java.util.function.Consumer;

/**
 * Base class for {@code notify()} and {@code notifyAll()} related tests.
 */
public abstract class NotifyTestBase extends NotifyWaitTestBase {

	protected static final String NOT_OWNER_TEST_NAME = "the current thread doesn't own a monitor";

	protected static void verifyThreads(final Thread[] threads, final Thread.State state) throws InterruptedException {
		Thread.sleep(1L);
		for (final Thread thread : threads)
			Assertions.assertEquals(state, thread.getState());
	}

	protected Thread[] notifyPositiveCase(final Consumer<? super UUT> notifyMethod) throws InterruptedException {
		final Thread[] waiters = startThreads(3, new Waiter(uut));
		verifyThreads(waiters, Thread.State.WAITING);
		synchronized (uut) {
			notifyMethod.accept(uut);
		}
		Thread.sleep(1L);
		return waiters;
	}
}
