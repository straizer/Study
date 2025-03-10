package semester5.pwjj.utils.persistence;

import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.ArgumentMatchers;
import semester5.pwjj.MockedConstruction;
import semester5.pwjj.utils.UtilsTestsBase;
import semester5.pwjj.utils.i18n.I18nProperty;


/** Abstract base class for {@code utils.persistence} test setups and utilities. */
@SuppressWarnings("ClassWithTooManyFields")
public abstract class UtilsPersistenceTestsBase extends UtilsTestsBase {

	protected static final I18nProperty ERROR_CANNOT_ADD_SHUTDOWN_HOOK
		= Messages.Error.CANNOT_ADD_SHUTDOWN_HOOK;
	protected static final I18nProperty ERROR_CLOSE_ENTITY_MANAGER_FACTORY_FAILED
		= Messages.Error.CLOSE_ENTITY_MANAGER_FACTORY_FAILED;
	protected static final I18nProperty ERROR_CLOSE_ENTITY_MANAGER_FAILED
		= Messages.Error.CLOSE_ENTITY_MANAGER_FAILED;
	protected static final I18nProperty ERROR_COMMIT_FAILED
		= Messages.Error.COMMIT_FAILED;
	protected static final I18nProperty ERROR_INVALID_HIBERNATE_CONFIG
		= Messages.Error.INVALID_HIBERNATE_CONFIG;
	protected static final I18nProperty ERROR_MISSING_DATABASE_PASSWORD
		= Messages.Error.MISSING_DATABASE_PASSWORD;
	protected static final I18nProperty ERROR_MISSING_DATABASE_USERNAME
		= Messages.Error.MISSING_DATABASE_USERNAME;
	protected static final I18nProperty ERROR_MISSING_HIBERNATE_CONFIG
		= Messages.Error.MISSING_HIBERNATE_CONFIG;
	protected static final I18nProperty ERROR_OPEN_ENTITY_MANAGER_FAILED
		= Messages.Error.OPEN_ENTITY_MANAGER_FAILED;
	protected static final I18nProperty ERROR_TRANSACTIONS_HANDLED_INTERNALLY
		= Messages.Error.TRANSACTIONS_HANDLED_INTERNALLY;
	protected static final I18nProperty ERROR_ROLLBACK_FAILED
		= Messages.Error.ROLLBACK_FAILED;

	/** Method executed once before all tests in each extending class. */
	@BeforeAll
	static void utilsPersistenceBeforeAll() throws IllegalAccessException {
		populateMessageProviderMock(UtilsPersistenceTestsBase.class);
	}

	@SuppressWarnings("UseOfConcreteClass")
	protected static MockedConstruction<Configuration> getPartialMockedConfiguration() {
		//noinspection resource
		return new MockedConstruction<>(Configuration.class)
			.when_thenReturnSelf(Configuration::configure)
			.when_thenReturnSelf(configuration ->
				configuration.setCredentials(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()));
	}
}
