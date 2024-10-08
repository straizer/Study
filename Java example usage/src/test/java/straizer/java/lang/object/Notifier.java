package straizer.java.lang.object;

/**
 * {@code Runnable} notifying {@code uut}.
 *
 * @param uut object to notify
 */
public record Notifier(UUT uut) implements Runnable {

	@Override
	public void run() {
		synchronized (uut) {
			uut.notifyAll();
		}
	}
}
