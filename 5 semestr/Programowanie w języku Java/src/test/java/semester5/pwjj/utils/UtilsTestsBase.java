package semester5.pwjj.utils;

import org.junit.jupiter.api.BeforeAll;
import semester5.pwjj.TestsBase;


/** Abstract base class for {@code utils} test setups and utilities. */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "unused"})
public abstract class UtilsTestsBase extends TestsBase {

	/** Method executed once before all tests in each extending class. */
	@BeforeAll
	static void utilsBeforeAll() throws IllegalAccessException {
		populateMessageProviderMock(UtilsTestsBase.class);
	}
}
