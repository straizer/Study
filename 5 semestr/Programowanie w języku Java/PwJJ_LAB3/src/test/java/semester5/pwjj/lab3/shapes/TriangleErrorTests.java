package semester5.pwjj.lab3.shapes;

import org.assertj.core.api.ThrowableAssert;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.lab3.Color;
import semester5.pwjj.lab3.ErrorTestsBase;
import semester5.pwjj.lab3.shapes.impl.Triangle;

import java.util.stream.Stream;

@DisplayName("Triangle Error Tests")
class TriangleErrorTests extends ErrorTestsBase {

	@NonNull
	private static Stream<Arguments> getPerimeterTriangleRuleNotFulfilledTest() {
		return Stream.of(
			Arguments.argumentSet("1 2 3", (ThrowableAssert.ThrowingCallable) () -> new Triangle(1, 2, 3, Color.RED)),
			Arguments.argumentSet("2 3 1", (ThrowableAssert.ThrowingCallable) () -> new Triangle(2, 3, 1, Color.RED)),
			Arguments.argumentSet("3 1 2", (ThrowableAssert.ThrowingCallable) () -> new Triangle(3, 1, 2, Color.RED)));
	}

	@DisplayName("non positive side length")
	@Test
	void getPerimeterNonPositiveSideTest() {
		throwsIllegalArgumentException(() -> new Triangle(0, 1, 1, Color.RED), ERROR_SIDES_NOT_POSITIVE);
	}

	@DisplayName("triangle rule isn't fulfilled")
	@ParameterizedTest
	@MethodSource
	void getPerimeterTriangleRuleNotFulfilledTest(final ThrowableAssert.ThrowingCallable callable) {
		throwsIllegalArgumentException(callable, ERROR_TRIANGLE_RULE);
	}
}
