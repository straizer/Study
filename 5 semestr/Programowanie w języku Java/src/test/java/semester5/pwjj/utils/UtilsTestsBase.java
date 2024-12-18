package semester5.pwjj.utils;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.BeforeAll;
import semester5.pwjj.TestsBase;
import semester5.pwjj.utils.i18n.I18nProperty;


/** Abstract base class for {@code utils} test setups and utilities. */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "unused"})
public abstract class UtilsTestsBase extends TestsBase {

	protected static final @NonNull I18nProperty ERROR_CANNOT_ADD_SHUTDOWN_HOOK
		= Messages.Error.CANNOT_ADD_SHUTDOWN_HOOK;
	protected static final @NonNull I18nProperty ERROR_CLOSE_SESSION_FACTORY_FAILED
		= Messages.Error.CLOSE_SESSION_FACTORY_FAILED;
	protected static final @NonNull I18nProperty ERROR_CLOSE_SESSION_FAILED
		= Messages.Error.CLOSE_SESSION_FAILED;
	protected static final @NonNull I18nProperty ERROR_COMMIT_FAILED
		= Messages.Error.COMMIT_FAILED;
	protected static final @NonNull I18nProperty ERROR_INVALID_HIBERNATE_CONFIG
		= Messages.Error.INVALID_HIBERNATE_CONFIG;
	protected static final @NonNull I18nProperty ERROR_MISSING_HIBERNATE_CONFIG
		= Messages.Error.MISSING_HIBERNATE_CONFIG;
	protected static final @NonNull I18nProperty ERROR_OPEN_SESSION_FAILED
		= Messages.Error.OPEN_SESSION_FAILED;
	protected static final @NonNull I18nProperty ERROR_TRANSACTIONS_HANDLED_INTERNALLY
		= Messages.Error.TRANSACTIONS_HANDLED_INTERNALLY;
	protected static final @NonNull I18nProperty ERROR_ROLLBACK_FAILED
		= Messages.Error.ROLLBACK_FAILED;

	/** Method executed once before all tests in each extending class. */
	@BeforeAll
	static void utilsBeforeAll() throws IllegalAccessException {
		populateMessageProviderMock(UtilsTestsBase.class);
	}
}
