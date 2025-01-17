package semester5.pwjj;

import lombok.Getter;
import lombok.experimental.Delegate;
import org.mockito.Mockito;

/**
 * The {@code MockedStatic} class provides a convenient wrapper for the {@link org.mockito.MockedStatic} class, enabling
 * controlled mocking of static methods. This class supports automatic resource management by implementing the
 * {@link AutoCloseable} interface to ensure that there are no unexpected interactions before closure.
 * @param <T> the type of the class whose static methods are being mocked
 */
@Getter
public final class MockedStatic<T> implements AutoCloseable {

	/**
	 * A delegated mock instance of {@link org.mockito.MockedStatic}, intended for managing static
	 * mocking behavior in a controlled way.
	 */
	@Delegate
	private final org.mockito.MockedStatic<T> mockedStatic;

	/**
	 * Constructs a new instance of {@code MockedStatic} for managing static mocking behavior
	 * of the specified class using Mockito's {@link org.mockito.MockedStatic}.
	 * @param mockedClass the {@link Class} object representing the class for which static methods are to be mocked
	 */
	public MockedStatic(final Class<T> mockedClass) {
		mockedStatic = Mockito.mockStatic(mockedClass);
	}

	/** Closes the mocked static instance, ensuring that there are no unexpected interactions before closure. */
	@Override
	public void close() {
		mockedStatic.verifyNoMoreInteractions();
		mockedStatic.close();
	}
}
