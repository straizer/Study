package semester5.pwjj;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.NonFinal;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import semester5.pwjj.utils.extensions.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * The {@code MockedConstruction} class provides a convenient wrapper for the {@link org.mockito.MockedConstruction}
 * class, enabling controlled mocking of new instances of type {@link T}. This class supports automatic resource
 * management by implementing the {@link AutoCloseable} interface to ensure that expected number of objects was created.
 * @param <T> the type of the class whose instances are being mocked
 */
@SuppressWarnings({"UseOfConcreteClass", "InstanceVariableMayNotBeInitialized"})
@RequiredArgsConstructor(onConstructor_ = @SuppressWarnings("initialization.fields.uninitialized"))
@ExtensionMethod(StringUtils.class)
public final class MockedConstruction<T> implements AutoCloseable {

	/** The class type being mocked. */
	Class<T> mockedClass;

	/** A mapping of methods to their respective throwable exceptions for controlled mocking behavior. */
	Map<Function<T, ?>, Throwable> throwingMockings = new HashMap<>(2);

	/** An instance of {@link org.mockito.MockedConstruction}, intended for managing new instances mocking behavior. */
	@NonFinal
	org.mockito.MockedConstruction<T> mockedConstruction;

	/** Indicates whether the mocking of new instances has been started. */
	@NonFinal
	boolean isStarted;

	/** Indicates whether the {@link IllegalStateException} has been thrown by this class. */
	@NonFinal
	boolean noIllegalStateExceptionThrown = true;

	/** Stores the currently selected method for mocking behavior configuration. */
	@NonFinal
	Function<T, ?> currentMethod;

	/**
	 * Specifies the method to be mocked.
	 * @param method the method reference on the mocked class
	 * @return the current {@code MockedConstruction} instance for chaining
	 * @throws IllegalStateException if the mocking has already been started
	 */
	public MockedConstruction<T> when(final Function<T, ?> method) {
		if (isStarted) {
			noIllegalStateExceptionThrown = false;
			throw new IllegalStateException(
				"The mocking of instances of <%s> class has already been started"
					.safeFormat(mockedClass.getSimpleName()));
		}
		currentMethod = method;
		return this;
	}

	/**
	 * Defines an exception to be thrown when the previously specified method is invoked.
	 * @param throwable the exception to be thrown
	 * @return the current {@code MockedConstruction} instance for chaining
	 * @throws IllegalStateException if the mocking has already been started
	 */
	public MockedConstruction<T> thenThrow(final Throwable throwable) {
		if (isStarted) {
			noIllegalStateExceptionThrown = false;
			throw new IllegalStateException(
				"The mocking of instances of <%s> class has already been started"
					.safeFormat(mockedClass.getSimpleName()));
		}
		throwingMockings.put(currentMethod, throwable);
		return this;
	}

	/**
	 * Starts the mocking process by creating and initializing the mocked construction.
	 * @return the current {@code MockedConstruction} instance for chaining
	 */
	public MockedConstruction<T> startMocking() {
		isStarted = true;
		mockedConstruction = Mockito.mockConstruction(mockedClass, (mock, _) ->
			throwingMockings.forEach((throwingMocking, throwable) ->
				Mockito.when(throwingMocking.apply(mock)).thenThrow(throwable)));
		return this;
	}

	/**
	 * Returns mocked instance for verification.
	 * @return the mocked instance for verification
	 * @throws IllegalStateException if the mocking hasn't been started yet
	 */
	public T verify() {
		if (!isStarted) {
			noIllegalStateExceptionThrown = false;
			throw new IllegalStateException(
				"The mocking of instances of <%s> class hasn't been started yet"
					.safeFormat(mockedClass.getSimpleName()));
		}
		return Mockito.verify(getMockedInstance());
	}

	/** Closes the mocking instance, ensuring that the expected number of objects was created. */
	@SuppressWarnings("argument")
	@Override
	public void close() {
		if (isStarted) {
			if (noIllegalStateExceptionThrown) {
				Mockito.verifyNoMoreInteractions(getMockedInstance());
			}
			mockedConstruction.close();
		}
	}

	/**
	 * Retrieves the single mocked instance that was created.
	 * @return the constructed mocked instance
	 * @throws AssertionError if more or fewer than one instance was created
	 */
	private T getMockedInstance() {
		final List<T> constructed = mockedConstruction.constructed();
		Assertions.assertThat(constructed.size()).describedAs("Exactly one instance should be created").isEqualTo(1);
		return constructed.getFirst();
	}
}
