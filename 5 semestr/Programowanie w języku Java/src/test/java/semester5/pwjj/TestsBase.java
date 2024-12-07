package semester5.pwjj;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.lang.reflect.Field;

/** Base class for all tests. */
public abstract class TestsBase {

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
	protected static final @NonNull I18nProperty UTILS_I18N_ERROR_FORMATTING
		= semester5.pwjj.utils.i18n.Messages.Error.FORMATTING;

	private static MockedStatic<MessageProvider> messageProviderMock;

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
	}

	@AfterAll
	static void globalAfterAll() {
		messageProviderMock.close();
	}
}
