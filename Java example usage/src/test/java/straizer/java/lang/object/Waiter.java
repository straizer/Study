package straizer.java.lang.object;

/**
 * {@code Runnable} waiting on {@code uut}.
 *
 * @param uut object to wait
 */
record Waiter(UUT uut) implements Runnable {

	@Override
	public void run() {
		try {
			synchronized (uut) {
				uut.wait();
			}
		} catch (final InterruptedException _) {
			Thread.currentThread().interrupt();
		}
	}
}
