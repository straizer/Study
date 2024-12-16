package semester5.pwjj;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.util.NullnessUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.lang.reflect.Field;

/** Base class for unit tests providing setup and utilities for consistent testing behavior. */
@SuppressWarnings({"ClassWithTooManyFields", "unused"})
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
	protected static final @NonNull I18nProperty ENTITIES_SHAPES_ERROR_SIDES_ARE_NULL
		= semester5.pwjj.entities.shapes.Messages.Error.SIDES_ARE_NULL;
	protected static final @NonNull I18nProperty ENTITIES_SHAPES_ERROR_SIDES_NOT_POSITIVE
		= semester5.pwjj.entities.shapes.Messages.Error.SIDES_NOT_POSITIVE;
	protected static final @NonNull I18nProperty ENTITIES_SHAPES_ERROR_TRIANGLE_RULE
		= semester5.pwjj.entities.shapes.Messages.Error.TRIANGLE_RULE;
	protected static final @NonNull I18nProperty ENTITIES_SHAPES_NAME_RECTANGLE
		= semester5.pwjj.entities.shapes.Messages.Name.RECTANGLE;
	protected static final @NonNull I18nProperty ENTITIES_SHAPES_NAME_TRIANGLE
		= semester5.pwjj.entities.shapes.Messages.Name.TRIANGLE;
	protected static final @NonNull I18nProperty ENTITIES_SHAPES_TO_STRING_SHAPE
		= semester5.pwjj.entities.shapes.Messages.ToString.SHAPE;
	protected static final @NonNull I18nProperty ENTITIES_TO_STRING_COLOR
		= semester5.pwjj.entities.Messages.ToString.COLOR;
	protected static final @NonNull I18nProperty UTILS_ERROR_CANNOT_ADD_SHUTDOWN_HOOK
		= semester5.pwjj.utils.Messages.Error.CANNOT_ADD_SHUTDOWN_HOOK;
	protected static final @NonNull I18nProperty UTILS_ERROR_CLOSE_SESSION_FACTORY_FAILED
		= semester5.pwjj.utils.Messages.Error.CLOSE_SESSION_FACTORY_FAILED;
	protected static final @NonNull I18nProperty UTILS_ERROR_CLOSE_SESSION_FAILED
		= semester5.pwjj.utils.Messages.Error.CLOSE_SESSION_FAILED;
	protected static final @NonNull I18nProperty UTILS_ERROR_COMMIT_FAILED
		= semester5.pwjj.utils.Messages.Error.COMMIT_FAILED;
	protected static final @NonNull I18nProperty UTILS_ERROR_INVALID_HIBERNATE_CONFIG
		= semester5.pwjj.utils.Messages.Error.INVALID_HIBERNATE_CONFIG;
	protected static final @NonNull I18nProperty UTILS_ERROR_MISSING_HIBERNATE_CONFIG
		= semester5.pwjj.utils.Messages.Error.MISSING_HIBERNATE_CONFIG;
	protected static final @NonNull I18nProperty UTILS_ERROR_OPEN_SESSION_FAILED
		= semester5.pwjj.utils.Messages.Error.OPEN_SESSION_FAILED;
	protected static final @NonNull I18nProperty UTILS_ERROR_TRANSACTIONS_HANDLED_INTERNALLY
		= semester5.pwjj.utils.Messages.Error.TRANSACTIONS_HANDLED_INTERNALLY;
	protected static final @NonNull I18nProperty UTILS_ERROR_ROLLBACK_FAILED
		= semester5.pwjj.utils.Messages.Error.ROLLBACK_FAILED;
	protected static final @NonNull I18nProperty UTILS_EXTENSIONS_ERROR_EXCEPTION_INSTANTIATION_FAILED
		= semester5.pwjj.utils.extensions.Messages.Error.EXCEPTION_INITIALIZATION_FAILED;
	protected static final @NonNull I18nProperty UTILS_EXTENSIONS_ERROR_FORMATTING
		= semester5.pwjj.utils.extensions.Messages.Error.FORMATTING;

	/**
	 * A static mock object for the {@link MessageProvider} class, used for testing purposes.
	 * This mock allows controlled manipulation and verification of interactions with the
	 * {@link MessageProvider} within test cases.
	 */
	private static @MonotonicNonNull MockedStatic<MessageProvider> messageProviderMock;

	/**
	 * This variable is statically shared across all tests to ensure consistent validation of
	 * the sequence in which the {@code messageProviderMock} is invoked.
	 */
	private static @MonotonicNonNull InOrder messageProviderInOrder;

	/**
	 * Initializes mocking of the {@link MessageProvider} class and sets up behavior for translation retrieval.
	 * @throws IllegalAccessException if reflection-based access to fields of {@code TestsBase} fails
	 */
	@BeforeAll
	static void globalBeforeAll() throws IllegalAccessException {
		messageProviderMock = Mockito.mockStatic(MessageProvider.class);
		for (final @NonNull Field field : TestsBase.class.getDeclaredFields()) {
			if (field.getType().equals(I18nProperty.class)) {
				field.setAccessible(true);
				@SuppressWarnings("argument") final @NonNull I18nProperty i18nProperty =
					(@NonNull I18nProperty) NullnessUtil.castNonNull(field.get(null));
				messageProviderMock
					.when(() -> MessageProvider.get(i18nProperty))
					.thenReturn(i18nProperty.getPropertyName());
			}
		}
		messageProviderInOrder = Mockito.inOrder(MessageProvider.class);
	}

	/** Ensures that the mocked {@code messageProviderMock} is closed. */
	@AfterAll
	static void globalAfterAll() {
		//noinspection StaticVariableUsedBeforeInitialization
		NullnessUtil.castNonNull(messageProviderMock).close();
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
			NullnessUtil.castNonNull(messageProviderMock), () -> MessageProvider.get(argument), Mockito.times(1));
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
