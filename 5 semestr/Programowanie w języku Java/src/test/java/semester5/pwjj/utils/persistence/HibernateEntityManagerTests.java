package semester5.pwjj.utils.persistence;

import org.junit.jupiter.api.DisplayName;
import semester5.pwjj.ErrorTestsBase;

@DisplayName("Hibernate Entity Manager Tests")
final class HibernateEntityManagerTests extends UtilsPersistenceTestsBase implements ErrorTestsBase {

//	@DisplayName("cannot add shutdown hook")
//	@Test
//	void cannotAddShutdownHookTest() {
//		final SessionFactory sessionFactoryMock = Mockito.mock(SessionFactory.class);
//		final Session sessionMock = Mockito.mock(Session.class);
//		final Transaction transactionMock = Mockito.mock(Transaction.class);
//		Mockito.when(sessionMock.getTransaction()).thenReturn(transactionMock);
//		Mockito.when(sessionFactoryMock.openSession()).thenReturn(sessionMock);
//		//noinspection UseOfConcreteClass
//		@Cleanup final MockedConstruction<Configuration> mockedConfiguration = getPartialMockedConfiguration()
//			.when_thenReturn(Configuration::buildSessionFactory, sessionFactoryMock).startMocking();
//		//noinspection UseOfConcreteClass
//		@Cleanup final MockedConstruction<Thread> mockedThread = new MockedConstruction<>(Thread.class)
//			.when_thenThrow(Thread::isAlive, new RuntimeException("")).startMocking();
//		@Cleanup final EntityManager entityManager = new HibernateEntityManager();
//		//noinspection ResultOfMethodCallIgnored
//		mockedThread.verify().isAlive();
//		mockedConfiguration.verify().configure();
//		mockedConfiguration.verify().setCredentials(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
//		//noinspection resource
//		mockedConfiguration.verify().buildSessionFactory();
//		//noinspection HibernateResourceOpenedButNotSafelyClosed
//		Mockito.verify(sessionFactoryMock).openSession();
//		Mockito.verify(sessionMock).getTransaction();
//		Mockito.verify(transactionMock).begin();
//		Mockito.verifyNoMoreInteractions(transactionMock);
//		Mockito.verifyNoMoreInteractions(sessionMock);
//		Mockito.verifyNoMoreInteractions(sessionFactoryMock);
//		verifyMessageProviderMockWasUsedFor(ERROR_CANNOT_ADD_SHUTDOWN_HOOK);
//	}

	//		final SessionFactory sessionFactoryMock = Mockito.mock(SessionFactory.class);
	//		final Session sessionMock = Mockito.mock(Session.class);
	//		final Transaction transactionMock = Mockito.mock(Transaction.class);
	//		Mockito.when(sessionMock.getTransaction()).thenReturn(transactionMock);
	//		Mockito.when(sessionFactoryMock.openSession()).thenReturn(sessionMock);
	//		//noinspection UseOfConcreteClass
	//		@Cleanup final MockedConstruction<Configuration> mockedConfiguration =
	//			new MockedConstruction<>(Configuration.class)
	//				.when_thenReturnSelf(Configuration::configure)
	//				.when_thenReturnSelf(configuration ->
	//					configuration.setCredentials(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
	//				.when_thenReturn(Configuration::buildSessionFactory, sessionFactoryMock)
	//				.startMocking();
	////		@Cleanup final MockedConstruction<Thread> mockedThread =
	////			new MockedConstruction<>(Thread.class).when_thenThrow(Thread::new, new RuntimeException()).startMocking();
	////		throwsHibernateException(HibernateEntityManager::new, ERROR_CANNOT_ADD_SHUTDOWN_HOOK.getPropertyName());
	//		new HibernateEntityManager();
	//		mockedConfiguration.verify().configure();
	//		mockedConfiguration.verify().setCredentials(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
	//		//noinspection resource
	//		mockedConfiguration.verify().buildSessionFactory();
	//		Mockito.verify(sessionFactoryMock).openSession();
	//		Mockito.verify(sessionMock).getTransaction();
	//		Mockito.verify(transactionMock).begin();
	////		mockedThread.verify();
	//		Mockito.verifyNoMoreInteractions(sessionFactoryMock);
	//		Mockito.verifyNoMoreInteractions(transactionMock);
	//		Mockito.verifyNoMoreInteractions(sessionMock);
	////		verifyMessageProviderMockWasUsedFor(ERROR_CANNOT_ADD_SHUTDOWN_HOOK);
}
