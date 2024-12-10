package semester5.pwjj.entities.shapes.impl;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.entities.Color;
import semester5.pwjj.entities.shapes.ShapeErrorTestsBase;
import semester5.pwjj.utils.i18n.MessageProvider;

import static org.mockito.Mockito.times;

@DisplayName("Rectangle Error Tests")
final class RectangleErrorTests extends ShapeErrorTestsBase {

	@BeforeAll
	static void beforeAll() {
		emptyShape = new Rectangle();
	}

	@DisplayName("non positive side length")
	@Test
	void getPerimeterNonPositiveSideTest() {
		throwsIllegalArgumentException(
			() -> new Rectangle(0, 1, Color.RED), ENTITIES_SHAPES_ERROR_SIDES_NOT_POSITIVE.getPropertyName());
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_SHAPES_NAME_RECTANGLE), times(1));
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_SHAPES_ERROR_SIDES_NOT_POSITIVE), times(1));
	}

	@DisplayName("no sides")
	@ParameterizedTest
	@MethodSource
	void noSidesTest(final @NonNull ThrowingCallable callable) {
		throwsIllegalStateException(callable, ENTITIES_SHAPES_ERROR_SIDES_ARE_NULL.getPropertyName());
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_SHAPES_NAME_RECTANGLE), times(1));
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_SHAPES_ERROR_SIDES_ARE_NULL), times(1));
	}
}
