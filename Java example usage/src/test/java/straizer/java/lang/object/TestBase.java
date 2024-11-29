package straizer.java.lang.object;

import org.junit.jupiter.api.BeforeEach;
import straizer.MainTestBase;

/**
 * Base class for {@link Object} tests.
 */
public abstract class TestBase extends MainTestBase<UUT> {

	protected static final int DEFAULT_N = 42;

	@BeforeEach
	@Override
	protected final void setup() {
		uut = new UUT(DEFAULT_N);
	}
}
