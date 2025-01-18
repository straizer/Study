package semester5.pwjj.utils.persistence;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import semester5.pwjj.ErrorTestsBase;
import semester5.pwjj.MockedConstruction;

@DisplayName("Hibernate Entity Manager Error Tests")
final class HibernateEntityManagerErrorTests extends UtilsPersistenceTestsBase implements ErrorTestsBase {

	@SuppressWarnings("UseOfConcreteClass")
	private static MockedConstruction<Configuration> mockedConfiguration;

	@BeforeAll
	static void beforeAll() {
		mockedConfiguration = new MockedConstruction<>(Configuration.class)
			.when(Configuration::configure)
			.thenThrow(new HibernateException(ERROR_MISSING_HIBERNATE_CONFIG.getPropertyName()))
			.startMocking();
	}

	@AfterAll
	static void afterAll() {
		//noinspection StaticVariableUsedBeforeInitialization
		mockedConfiguration.close();
	}

	private static void throwsHibernateException(final ThrowingCallable callable, final String message) {
		ErrorTestsBase.throwsException(callable, HibernateException.class, message);
	}

	@DisplayName("missing Hibernate config file")
	@Test
	void missingHibernateConfigFileTest() {
		throwsHibernateException(HibernateEntityManager::new, ERROR_MISSING_HIBERNATE_CONFIG.getPropertyName());
		mockedConfiguration.verify().configure();
		verifyMessageProviderMockWasUsedFor(ERROR_MISSING_HIBERNATE_CONFIG);
	}
}
