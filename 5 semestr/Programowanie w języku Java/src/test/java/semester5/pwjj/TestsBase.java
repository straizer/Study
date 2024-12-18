package semester5.pwjj;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.util.NullnessUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InOrder;
import org.mockito.Mockito;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.lang.reflect.Field;

/** Base class for unit tests providing setup and utilities for consistent testing behavior. */
@SuppressWarnings("unused")
public abstract class TestsBase {

	protected static final @NonNull I18nProperty DAO_ERROR_CREATE_ENTITY_MANAGER_FAILED
		= semester5.pwjj.dao.Messages.Error.CREATE_ENTITY_MANAGER_FAILED;
	protected static final @NonNull I18nProperty DAO_ERROR_SIDES_ARE_NULL
		= semester5.pwjj.dao.Messages.Error.ENTITY_ALREADY_EXISTS;
	protected static final @NonNull I18nProperty DAO_ERROR_NOT_AN_ENTITY_TYPE
		= semester5.pwjj.dao.Messages.Error.NOT_AN_ENTITY_TYPE;
	protected static final @NonNull I18nProperty DAO_ERROR_NOT_AN_ENTITY_TYPE_OR_REMOVED
		= semester5.pwjj.dao.Messages.Error.NOT_AN_ENTITY_TYPE_OR_REMOVED;
	protected static final @NonNull I18nProperty DAO_ERROR_UNEXPECTED_TYPE
		= semester5.pwjj.dao.Messages.Error.UNEXPECTED_TYPE;

	/**
	 * A static mock object for the {@link MessageProvider} class, used for testing purposes.
	 * This mock allows controlled manipulation and verification of interactions with the
	 * {@link MessageProvider} within test cases.
	 */
	@SuppressWarnings("UseOfConcreteClass")
	private static @MonotonicNonNull MockedStatic<MessageProvider> messageProviderMock;

	/**
	 * This variable is statically shared across all tests to ensure consistent validation of
	 * the sequence in which the {@code messageProviderMock} is invoked.
	 */
	private static @MonotonicNonNull InOrder messageProviderInOrder;

	/**
	 * Initializes mocking of the {@link MessageProvider} class.
	 * @throws IllegalAccessException if reflection-based access to fields of {@code TestsBase} fails
	 */
	@BeforeAll
	static void globalBeforeAll() throws IllegalAccessException {
		messageProviderMock = new MockedStatic<>(MessageProvider.class);
		messageProviderInOrder = Mockito.inOrder(MessageProvider.class);
		populateMessageProviderMock(TestsBase.class);
	}

	/** Ensures that the mocked {@code messageProviderMock} is closed. */
	@AfterAll
	static void globalAfterAll() {
		//noinspection StaticVariableUsedBeforeInitialization
		NullnessUtil.castNonNull(messageProviderMock).close();
	}

	/**
	 * Populates the mocked {@link MessageProvider} for the specified {@code TestsBase} subclass.
	 * It configures the mock behavior such that whenever {@link MessageProvider#get(I18nProperty)}
	 * is invoked with an identified {@link I18nProperty} field, it returns the property name
	 * of that {@link I18nProperty}.
	 * @param clazz the {@link Class} object representing a subclass of {@code TestsBase},
	 *              whose {@link I18nProperty} fields will be used to set up the mock behavior
	 * @throws IllegalAccessException if the field access operations via reflection fail
	 */
	protected static void populateMessageProviderMock(
		final @NonNull Class<? extends @NonNull TestsBase> clazz
	) throws IllegalAccessException {
		for (final @NonNull Field field : clazz.getDeclaredFields()) {
			if (field.getType().equals(I18nProperty.class)) {
				field.setAccessible(true);
				@SuppressWarnings("argument") final @NonNull I18nProperty i18nProperty =
					(@NonNull I18nProperty) NullnessUtil.castNonNull(field.get(null));
				NullnessUtil.castNonNull(messageProviderMock)
					.when(() -> MessageProvider.get(i18nProperty))
					.thenReturn(i18nProperty.getPropertyName());
			}
		}
	}


	/**
	 * Verifies that the mock instance of the {@code MessageProvider} was invoked exactly once
	 * for retrieving the translation of the specified {@link I18nProperty}.
	 * @param argument the {@link I18nProperty} representing the message key for which
	 *                 the {@code MessageProvider} mock should have been used
	 */
	protected static void verifyMessageProviderMockWasUsedFor(final @NonNull I18nProperty argument) {
		//noinspection StaticVariableUsedBeforeInitialization
		NullnessUtil.castNonNull(messageProviderInOrder).verify(
			NullnessUtil.castNonNull(messageProviderMock).getMockedStatic(), () -> MessageProvider.get(argument));
	}

	/**
	 * Clears the recorded invocations of the {@code messageProviderMock} to ensure a clean state before
	 * running the next test.
	 */
	@BeforeEach
	void globalBeforeEach() {
		NullnessUtil.castNonNull(messageProviderMock).clearInvocations();
	}

	/** Verifies that no unexpected interactions occurred with the {@code messageProviderMock}. */
	@AfterEach
	void globalAfterEach() {
		NullnessUtil.castNonNull(messageProviderMock).verifyNoMoreInteractions();
	}
}
