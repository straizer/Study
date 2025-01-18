package semester5.pwjj;

/**
 * The {@code MockedConstruction} class provides a convenient wrapper for the {@link org.mockito.MockedConstruction}
 * class, enabling controlled mocking of new instances of type {@link T}. This class supports automatic resource
 * management by implementing the {@link AutoCloseable} interface to ensure that expected number of objects was created.
 * @param <T> the type of the class whose instances are being mocked
 */
public final class MockedConstruction<T> implements AutoCloseable {

//	Class<T> mockedClass;
//	Map<Function<T, ?>, Throwable> throwingMockings = new HashMap<>();
//	/** An instance of {@link org.mockito.MockedConstruction}, intended for managing new instances mocking behavior. */
//	@NonFinal
//	org.mockito.MockedConstruction<T> mockedConstruction;
//	@NonFinal
//	boolean isCreated;
//	@NonFinal
//	Function<T, ?> currentMethod;
//
//	/**
//	 * Constructs a new instance of {@code MockedConstruction} for managing new instances mocking behavior
//	 * of the specified class using Mockito's {@link org.mockito.MockedConstruction}.
//	 * @param mockedClass the {@link Class} object representing the class for which new instances are to be mocked
//	 */
//	public MockedConstruction(final Class<T> mockedClass) {
//		this.mockedClass = mockedClass;
//	}
//
//	public MockedConstruction<T> when(final Function<T, ?> method) {
//		if (isCreated) {
//			throw new RuntimeException("");
//		}
//		currentMethod = method;
//		return this;

	/// /		return new OngoingStubbing<>(Mockito.when(methodCall));
//	}
//
//	public MockedConstruction<T> thenThrow(final Throwable throwable) {
//		if (isCreated) {
//			throw new RuntimeException("");
//		}
//		throwingMockings.put(currentMethod, throwable);
//		return this;
//	}
//
//	public MockedConstruction<T> create() {
//		isCreated = true;
//		mockedConstruction = Mockito.mockConstruction(mockedClass, (mock, _) -> {
//			throwingMockings.forEach((throwingMocking, throwable) ->
//				Mockito.when(mock -> ).thenThrow(throwable));
//		});
//	}
//
//	/** Closes the mocking instance, ensuring that the expected number of objects was created. */
	@Override
	public void close() {
//		final List<T> constructed = mockedConstruction.constructed();
//		Assertions.assertThat(constructed.size()).isEqualTo(1);
//		Mockito.verifyNoMoreInteractions(constructed.getFirst());
//		mockedConstruction.close();
	}
}
