package semester5.pwjj.utils.extensions;

import org.junit.jupiter.api.BeforeAll;
import semester5.pwjj.utils.UtilsTestsBase;
import semester5.pwjj.utils.i18n.I18nProperty;

/** Abstract base class for {@code utils.extensions} test setups and utilities. */
public abstract class UtilsExtensionsTestsBase extends UtilsTestsBase {

	protected static final I18nProperty ERROR_FORMATTING = Messages.Error.FORMATTING;

	/** Method executed once before all tests in each extending class. */
	@BeforeAll
	static void utilsExtensionsBeforeAll() throws IllegalAccessException {
		populateMessageProviderMock(UtilsExtensionsTestsBase.class);
	}
}
