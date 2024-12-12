package semester5.pwjj.entities.shapes.impl;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.entities.Color;
import semester5.pwjj.entities.shapes.ShapeErrorTestsBase;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.stream.Stream;

import static org.mockito.Mockito.times;

@DisplayName("Triangle Error Tests")
final class TriangleErrorTests extends ShapeErrorTestsBase {

	/** Method to execute before all tests. */
	@BeforeAll
	static void beforeAll() {
		emptyShape = new Triangle();
	}

	/** Method supplying {@link ParameterizedTest} with the same name. */
	private static @NonNull Stream<Arguments> getPerimeterTriangleRuleNotFulfilledTest() {
		return Stream.of(
			Arguments.argumentSet("1 2 3", (ThrowingCallable) () -> new Triangle(1, 2, 3, Color.RED)),
			Arguments.argumentSet("2 3 1", (ThrowingCallable) () -> new Triangle(2, 3, 1, Color.RED)),
			Arguments.argumentSet("3 1 2", (ThrowingCallable) () -> new Triangle(3, 1, 2, Color.RED)));
	}

	@DisplayName("non positive side length")
	@Test
	void getPerimeterNonPositiveSideTest() {
		throwsIllegalArgumentException(
			() -> new Triangle(0, 1, 1, Color.RED), ENTITIES_SHAPES_ERROR_SIDES_NOT_POSITIVE.getPropertyName());
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_SHAPES_NAME_TRIANGLE), times(1));
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_SHAPES_ERROR_SIDES_NOT_POSITIVE), times(1));
	}

	@DisplayName("triangle rule isn't fulfilled")
	@ParameterizedTest
	@MethodSource
	void getPerimeterTriangleRuleNotFulfilledTest(final @NonNull ThrowingCallable callable) {
		throwsIllegalArgumentException(callable, ENTITIES_SHAPES_ERROR_TRIANGLE_RULE.getPropertyName());
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_SHAPES_ERROR_TRIANGLE_RULE), times(1));
	}

	@DisplayName("no sides")
	@ParameterizedTest
	@MethodSource
	void noSidesTest(final @NonNull ThrowingCallable callable) {
		throwsIllegalStateException(callable, ENTITIES_SHAPES_ERROR_SIDES_ARE_NULL.getPropertyName());
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_SHAPES_NAME_TRIANGLE), times(1));
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_SHAPES_ERROR_SIDES_ARE_NULL), times(1));
	}
}
