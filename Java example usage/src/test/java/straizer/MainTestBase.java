package straizer;

/**
 * Base class for all tests.
 *
 * @param <T> type of unit under test (UUT)
 */
public abstract class MainTestBase<T> {

	protected T uut;

	protected abstract void setup();
}
