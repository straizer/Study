package semester5.pwjj.utils.persistence;

import lombok.Cleanup;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.util.NullnessUtil;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic.Verification;
import org.mockito.Mockito;
import semester5.pwjj.ErrorTestsBase;
import semester5.pwjj.MockedConstruction;
import semester5.pwjj.MockedStatic;
import semester5.pwjj.utils.EnvProperties;

import java.util.function.UnaryOperator;

@DisplayName("Hibernate Entity Manager Error Tests")
final class HibernateEntityManagerErrorTests extends UtilsPersistenceTestsBase implements ErrorTestsBase {

	private static final Verification gettingDatabaseUsername = () -> EnvProperties.get("DATABASE_USERNAME");
	private static final Verification gettingDatabasePassword = () -> EnvProperties.get("DATABASE_PASSWORD");
	private static final Verification gettingNonNullValue =
		() -> EnvProperties.get(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());

	@SuppressWarnings("UseOfConcreteClass")
	private static @MonotonicNonNull MockedStatic<EnvProperties> envPropertiesMock;

	@BeforeAll
	static void beforeAll() {
		envPropertiesMock = new MockedStatic<>(EnvProperties.class);
		envPropertiesMock.when(gettingNonNullValue).thenCallRealMethod();
	}

	@AfterAll
	static void afterAll() {
		//noinspection StaticVariableUsedBeforeInitialization
		NullnessUtil.castNonNull(envPropertiesMock).close();
	}

	private static void throwsHibernateException(final ThrowingCallable callable, final String message) {
		ErrorTestsBase.throwsException(callable, HibernateException.class, message);
	}

	private static void runTestsUsingMockedConfiguration(
		final UnaryOperator<MockedConstruction<Configuration>> mockedConfigurationModifier, final Runnable test
	) {
		//noinspection UseOfConcreteClass
		final MockedConstruction<Configuration> mockedConfiguration =
			mockedConfigurationModifier.apply(getPartialMockedConfiguration()).startMocking();
		test.run();
		mockedConfiguration.verify().configure();
		mockedConfiguration.verify().setCredentials(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
		//noinspection resource
		mockedConfiguration.verify().buildSessionFactory();
	}

	@BeforeEach
	void beforeEach() {
		NullnessUtil.castNonNull(envPropertiesMock);
		envPropertiesMock.when(gettingDatabaseUsername).thenReturn("user");
		envPropertiesMock.when(gettingDatabasePassword).thenReturn("pass");
	}

	@AfterEach
	void afterEach() {
		NullnessUtil.castNonNull(envPropertiesMock).clearInvocations();
	}

	@DisplayName("missing Hibernate config")
	@Test
	void missingHibernateConfigTest() {
		//noinspection UseOfConcreteClass
		@Cleanup final MockedConstruction<Configuration> mockedConfiguration =
			new MockedConstruction<>(Configuration.class)
				.when_thenThrow(Configuration::configure,
					new HibernateException(ERROR_MISSING_HIBERNATE_CONFIG.getPropertyName()))
				.startMocking();
		throwsHibernateException(HibernateEntityManager::new, ERROR_MISSING_HIBERNATE_CONFIG.getPropertyName());
		NullnessUtil.castNonNull(mockedConfiguration).verify().configure();
		verifyMessageProviderMockWasUsedFor(ERROR_MISSING_HIBERNATE_CONFIG);
	}

	@SuppressWarnings("argument")
	@DisplayName("missing database username")
	@Test
	void missingDatabaseUsernameTest() {
		//noinspection UseOfConcreteClass
		NullnessUtil.castNonNull(envPropertiesMock).when(gettingDatabaseUsername).thenReturn(null);
		throwsHibernateException(HibernateEntityManager::new, ERROR_MISSING_DATABASE_USERNAME.getPropertyName());
		envPropertiesMock.verify(gettingDatabaseUsername);
		envPropertiesMock.verify(gettingNonNullValue);
		verifyMessageProviderMockWasUsedFor(ERROR_MISSING_DATABASE_USERNAME);
	}

	@SuppressWarnings("argument")
	@DisplayName("missing database password")
	@Test
	void missingDatabasePasswordTest() {
		NullnessUtil.castNonNull(envPropertiesMock).when(gettingDatabasePassword).thenReturn(null);
		throwsHibernateException(HibernateEntityManager::new, ERROR_MISSING_DATABASE_PASSWORD.getPropertyName());
		envPropertiesMock.verify(gettingDatabaseUsername);
		envPropertiesMock.verify(gettingDatabasePassword);
		envPropertiesMock.verify(gettingNonNullValue, Mockito.times(2));
		verifyMessageProviderMockWasUsedFor(ERROR_MISSING_DATABASE_PASSWORD);
	}

	@DisplayName("invalid hibernate config")
	@Test
	void invalidHibernateConfigTest() {
		runTestsUsingMockedConfiguration(mockedConfiguration -> mockedConfiguration
				.when_thenThrow(Configuration::buildSessionFactory,
					new HibernateException(ERROR_INVALID_HIBERNATE_CONFIG.getPropertyName())),
			() -> {
				throwsHibernateException(HibernateEntityManager::new, ERROR_INVALID_HIBERNATE_CONFIG.getPropertyName());
				verifyMessageProviderMockWasUsedFor(ERROR_INVALID_HIBERNATE_CONFIG);
			});
	}

	@DisplayName("open entity manager failed")
	@Test
	void openEntityManagerFailedTest() {
		//noinspection resource
		final SessionFactory sessionFactoryMock = Mockito.mock(SessionFactory.class);
		Mockito.when(sessionFactoryMock.openSession())
			.thenThrow(new HibernateException(ERROR_OPEN_ENTITY_MANAGER_FAILED.getPropertyName()));
		//noinspection OverlyLongLambda
		runTestsUsingMockedConfiguration(mockedConfiguration -> mockedConfiguration
				.when_thenReturn(Configuration::buildSessionFactory, sessionFactoryMock),
			() -> {
				throwsHibernateException(HibernateEntityManager::new,
					ERROR_OPEN_ENTITY_MANAGER_FAILED.getPropertyName());
				//noinspection HibernateResourceOpenedButNotSafelyClosed
				Mockito.verify(sessionFactoryMock).openSession();
				Mockito.verifyNoMoreInteractions(sessionFactoryMock);
				verifyMessageProviderMockWasUsedFor(ERROR_OPEN_ENTITY_MANAGER_FAILED);
			});
	}
}
