package semester5.pwjj.utils.persistence;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import semester5.pwjj.ErrorTestsBase;

import java.util.function.Supplier;

@DisplayName("Hibernate Entity Manager Error Tests")
final class HibernateEntityManagerErrorTests extends UtilsPersistenceTestsBase implements ErrorTestsBase {

	private static void throwsHibernateException(final ThrowingCallable callable, final String message) {
		ErrorTestsBase.throwsException(callable, HibernateException.class, message);
	}

	@DisplayName("missing Hibernate config file")
	@Test
	void missingHibernateConfigFileTest() {
//		@Cleanup final MockedConstruction<Configuration> mockedConfiguration
		try (final MockedConstruction<Configuration> configurationMock =
			     Mockito.mockConstruction(Configuration.class, ((mock, _) -> {
				     final Supplier<Configuration> x = mock::configure;
				     Mockito.when(x.get())
					     .thenThrow(new HibernateException(ERROR_MISSING_HIBERNATE_CONFIG.getPropertyName()));
			     }))) {
			throwsHibernateException(HibernateEntityManager::new, ERROR_MISSING_HIBERNATE_CONFIG.getPropertyName());
			Assertions.assertThat(configurationMock.constructed().size()).isEqualTo(1);
		}
		verifyMessageProviderMockWasUsedFor(ERROR_MISSING_HIBERNATE_CONFIG);
	}
}
