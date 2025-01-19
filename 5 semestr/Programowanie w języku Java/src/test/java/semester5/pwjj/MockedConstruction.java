package semester5.pwjj;

import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.NonFinal;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import semester5.pwjj.utils.extensions.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
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

	/** A list of consumer functions defining the mocking behavior to be applied to created instances. */
	Collection<Consumer<T>> mockings = new ArrayList<>(10);

	/** An instance of {@link org.mockito.MockedConstruction}, intended for managing new instances mocking behavior. */
	@NonFinal
	org.mockito.MockedConstruction<T> mockedConstruction;

	/** Indicates whether the mocking of new instances has been started. */
	@NonFinal
	boolean isStarted;

	/** Indicates whether the {@link IllegalStateException} has been thrown by this class. */
	@NonFinal
	boolean noIllegalStateExceptionThrown = true;

	/**
	 * Specifies an exception to be thrown when the given method is invoked on a mocked instance.
	 * @param method    the method whose invocation should trigger the exception
	 * @param throwable the exception to be thrown
	 * @return the current {@code MockedConstruction} instance for chaining
	 * @throws IllegalStateException if the mocking has already been started
	 */
	public MockedConstruction<T> when_thenThrow(final Function<? super T, ?> method, final Throwable throwable) {
		return addMockings(mock -> Mockito.when(method.apply(mock)).thenThrow(throwable));
	}

	/**
	 * Specifies a predefined return value for the given method when invoked on a mocked instance.
	 * @param method the method whose invocation should return the specified value
	 * @param value  the value to be returned
	 * @param <R>    the return type of the method
	 * @return the current {@code MockedConstruction} instance for chaining
	 * @throws IllegalStateException if the mocking has already been started
	 */
	public <R> MockedConstruction<T> when_thenReturn(final Function<? super T, R> method, final R value) {
		return addMockings(mock -> Mockito.when(method.apply(mock)).thenReturn(value));
	}

	/**
	 * Configures the given method to return the mocked instance itself when invoked.
	 * @param method the method that should return {@code this} when called
	 * @return the current {@code MockedConstruction} instance for chaining
	 * @throws IllegalStateException if the mocking has already been started
	 */
	public MockedConstruction<T> when_thenReturnSelf(final Function<? super T, ?> method) {
		return addMockings(mock -> Mockito.when(method.apply(mock)).thenAnswer(InvocationOnMock::getMock));
	}

	/**
	 * Starts the mocking process by creating and initializing the mocked construction.
	 * @return the current {@code MockedConstruction} instance for chaining
	 */
	public MockedConstruction<T> startMocking() {
		mockedConstruction = Mockito.mockConstruction(mockedClass, (mock, _) ->
			mockings.forEach(consumer -> consumer.accept(mock)));
		isStarted = true;
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
		Assertions.assertThat(constructed.size())
			.describedAs("Exactly one instance of type <%s> should be created".safeFormat(mockedClass.getSimpleName()))
			.isEqualTo(1);
		return constructed.getFirst();
	}

	/**
	 * Adds a mocking behavior to be applied when a new instance of the mocked class is created.
	 * @param mocking the consumer defining the mocking behavior to be applied
	 * @return the current {@code MockedConstruction} instance for chaining
	 * @throws IllegalStateException if the mocking has already been started
	 */
	private @NonNull MockedConstruction<T> addMockings(final Consumer<T> mocking) {
		if (isStarted) {
			noIllegalStateExceptionThrown = false;
			throw new IllegalStateException(
				"The mocking of instances of <%s> class has already been started"
					.safeFormat(mockedClass.getSimpleName()));
		}
		mockings.add(mocking);
		return this;
	}
}
