package semester5.pwjj;

import org.checkerframework.checker.nullness.qual.NonNull;
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

/** Base class for all tests. */
public abstract class TestsBase {

	protected static final @NonNull I18nProperty DAO_ERROR_SIDES_ARE_NULL
		= semester5.pwjj.dao.Messages.Error.ENTITY_ALREADY_EXISTS;
	protected static final @NonNull I18nProperty DAO_ERROR_NOT_AN_ENTITY_TYPE
		= semester5.pwjj.dao.Messages.Error.NOT_AN_ENTITY_TYPE;
	protected static final @NonNull I18nProperty DAO_ERROR_NOT_AN_ENTITY_TYPE_OR_REMOVED
		= semester5.pwjj.dao.Messages.Error.NOT_AN_ENTITY_TYPE_OR_REMOVED;
	protected static final @NonNull I18nProperty DAO_ERROR_OPEN_SESSION_FAILED
		= semester5.pwjj.dao.Messages.Error.OPEN_SESSION_FAILED;
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
	protected static final @NonNull I18nProperty UTILS_ERROR_CLASS_NOT_SERIALIZABLE
		= semester5.pwjj.utils.Messages.Error.CLASS_NOT_SERIALIZABLE;
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
		= semester5.pwjj.utils.extensions.Messages.Error.EXCEPTION_INSTANTIATION_FAILED;
	protected static final @NonNull I18nProperty UTILS_EXTENSIONS_ERROR_FORMATTING
		= semester5.pwjj.utils.extensions.Messages.Error.FORMATTING;

	private static MockedStatic<MessageProvider> messageProviderMock;
	private static InOrder messageProviderInOrder;

	/**
	 * Method to execute before all tests in each extending class.
	 * @throws IllegalAccessException if {@link I18nProperty} in this class is inaccessible. It should never throw.
	 */
	@BeforeAll
	static void globalBeforeAll() throws IllegalAccessException {
		messageProviderMock = Mockito.mockStatic(MessageProvider.class);
		for (final @NonNull Field field : TestsBase.class.getDeclaredFields()) {
			if (field.getType().equals(I18nProperty.class)) {
				field.setAccessible(true);
				final @NonNull I18nProperty i18nProperty = (I18nProperty) field.get(null);
				messageProviderMock
					.when(() -> MessageProvider.get(i18nProperty))
					.thenReturn(i18nProperty.getPropertyName());
			}
		}
		messageProviderInOrder = Mockito.inOrder(MessageProvider.class);
	}

	/** Method to execute after all tests in each extending class. */
	@AfterAll
	static void globalAfterAll() {
		messageProviderMock.close();
	}

	protected static void verifyMessageProviderMockWasUsedFor(final @NonNull I18nProperty argument) {
		messageProviderInOrder.verify(
			messageProviderMock, () -> MessageProvider.get(argument), Mockito.times(1));
	}

	/** Method to execute before each test in each extending class. */
	@BeforeEach
	void globalBeforeEach() {
		messageProviderMock.clearInvocations();
	}

	/** Method to execute after each test in each extending class. */
	@AfterEach
	void globalAfterEach() {
		messageProviderMock.verifyNoMoreInteractions();
	}
}
