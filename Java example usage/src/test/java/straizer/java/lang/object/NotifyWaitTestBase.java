package straizer.java.lang.object;

/**
 * Base class for {@code notify()}, {@code notifyAll()} and {@code wait()} related tests.
 */
public abstract class NotifyWaitTestBase extends TestBase {

	protected static Thread[] startThreads(final int count, final Runnable runnable) {
		final Thread[] threads = new Thread[count];
		for (int i = 0; i < count; i++) {
			threads[i] = new Thread(runnable);
			threads[i].start();
		}
		return threads;
	}
}
