package semester5.pwjj.utils;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.BeforeAll;
import semester5.pwjj.TestsBase;
import semester5.pwjj.utils.i18n.I18nProperty;


/** Abstract base class for {@code utils} test setups and utilities. */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "unused"})
public abstract class UtilsTestsBase extends TestsBase {

	protected static final @NonNull I18nProperty NO_FILE_FOUND = Messages.Error.NO_FILE_FOUND;
	protected static final @NonNull I18nProperty NO_READ_ACCESS = Messages.Error.NO_READ_ACCESS;
	protected static final @NonNull I18nProperty READING_FAILED = Messages.Error.READING_FAILED;

	/** Method executed once before all tests in each extending class. */
	@BeforeAll
	static void utilsBeforeAll() throws IllegalAccessException {
		populateMessageProviderMock(UtilsTestsBase.class);
	}
}
