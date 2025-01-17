package semester5.pwjj.entities;

import org.junit.jupiter.api.BeforeAll;
import semester5.pwjj.TestsBase;
import semester5.pwjj.utils.i18n.I18nProperty;

/** Abstract base class for {@code entities} test setups and utilities. */
@SuppressWarnings("ClassNamePrefixedWithPackageName")
public abstract class EntitiesTestsBase extends TestsBase {

	protected static final I18nProperty TO_STRING_COLOR = Messages.ToString.COLOR;

	/** Method executed once before all tests in each extending class. */
	@BeforeAll
	static void entitiesBeforeAll() throws IllegalAccessException {
		populateMessageProviderMock(EntitiesTestsBase.class);
	}
}
