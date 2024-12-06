package semester5.pwjj.lab3;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import semester5.pwjj.lab3.i18n.MessageProvider;

import java.lang.reflect.Field;

/**
 * Base class for all tests.
 */
public abstract class TestsBase {

	protected static final String ERROR_SIDES_NOT_POSITIVE = "error.sidesNotPositive";
	protected static final String ERROR_TRIANGLE_RULE = "error.triangleRule";
	protected static final String NAME_RECTANGLE = "name.rectangle";
	protected static final String NAME_TRIANGLE = "name.triangle";
	protected static final String TO_STRING_COLOR = "toString.color";
	protected static final String TO_STRING_SHAPE = "toString.shape";

	private static MockedStatic<MessageProvider> messageProviderMock = null;

	@BeforeAll
	static void globalBeforeAll() throws IllegalAccessException {
		messageProviderMock = Mockito.mockStatic(MessageProvider.class);
		for (final Field field : TestsBase.class.getDeclaredFields()) {
			if (field.getType().equals(String.class)) {
				field.setAccessible(true);
				final String property = (String) field.get(null);
				messageProviderMock
					.when(() -> MessageProvider.get(new MessageProvider.I18nProperty(property)))
					.thenReturn(property);
			}
		}
	}

	@AfterAll
	static void globalAfterAll() {
		messageProviderMock.close();
	}
}
