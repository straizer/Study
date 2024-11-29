package straizer.java.lang.object;

/**
 * {@link Runnable} interrupting {@code thread}.
 *
 * @param thread thread to interrupt
 */
public record Interrupter(Thread thread) implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(1L);
		} catch (final InterruptedException _) {
			Thread.currentThread().interrupt();
		}
		thread.interrupt();
	}
}
