package semester5.pwjj.entities.shapes;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.entities.Color;
import semester5.pwjj.entities.shapes.impl.Triangle;

import java.util.stream.Stream;

@DisplayName("Triangle Error Tests")
final class TriangleErrorTests extends ShapeErrorTestsBase {

	@BeforeAll
	static void beforeAll() {
		emptyShape = new Triangle();
	}

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
	}

	@DisplayName("triangle rule isn't fulfilled")
	@ParameterizedTest
	@MethodSource
	void getPerimeterTriangleRuleNotFulfilledTest(final @NonNull ThrowingCallable callable) {
		throwsIllegalArgumentException(callable, ENTITIES_SHAPES_ERROR_TRIANGLE_RULE.getPropertyName());
	}

	@DisplayName("no sides")
	@ParameterizedTest
	@MethodSource
	void noSidesTest(final @NonNull ThrowingCallable callable) {
		throwsIllegalStateException(callable, ENTITIES_SHAPES_ERROR_SIDES_ARE_NULL.getPropertyName());
	}
}
