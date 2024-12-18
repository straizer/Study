package semester5.pwjj.entities.shapes.impl;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.ErrorTestsBase;
import semester5.pwjj.entities.Color;

import java.util.stream.Stream;

@DisplayName("Triangle Error Tests")
final class TriangleErrorTests extends EntitiesShapesImplErrorTestsBase {

	/** Method executed once before all tests. */
	@BeforeAll
	static void beforeAll() {
		emptyShape = new Triangle();
	}

	/** Method supplying {@link #constructorTriangleRuleNotFulfilledTest(ThrowingCallable). */
	private static @NonNull Stream<Arguments> constructorTriangleRuleNotFulfilledTest() {
		return Stream.of(
			Arguments.argumentSet("1 2 3", (ThrowingCallable) () -> new Triangle(1, 2, 3, Color.RED)),
			Arguments.argumentSet("2 3 1", (ThrowingCallable) () -> new Triangle(2, 3, 1, Color.RED)),
			Arguments.argumentSet("3 1 2", (ThrowingCallable) () -> new Triangle(3, 1, 2, Color.RED)));
	}

	@DisplayName("constructor non positive side")
	@Test
	void constructorNonPositiveSideTest() {
		ErrorTestsBase.throwsIllegalArgumentException(
			() -> new Triangle(0, 1, 1, Color.RED), ERROR_SIDES_NOT_POSITIVE.getPropertyName());
		verifyMessageProviderMockWasUsedFor(NAME_TRIANGLE);
		verifyMessageProviderMockWasUsedFor(ERROR_SIDES_NOT_POSITIVE);
	}

	@DisplayName("constructor triangle rule not fulfilled")
	@ParameterizedTest
	@MethodSource
	void constructorTriangleRuleNotFulfilledTest(final @NonNull ThrowingCallable callable) {
		ErrorTestsBase.throwsIllegalArgumentException(callable, ERROR_TRIANGLE_RULE.getPropertyName());
		verifyMessageProviderMockWasUsedFor(ERROR_TRIANGLE_RULE);
	}

	@DisplayName("no sides")
	@ParameterizedTest
	@MethodSource
	void noSidesTest(final @NonNull ThrowingCallable callable) {
		ErrorTestsBase.throwsIllegalStateException(callable, ERROR_SIDES_ARE_NULL.getPropertyName());
		verifyMessageProviderMockWasUsedFor(NAME_TRIANGLE);
		verifyMessageProviderMockWasUsedFor(ERROR_SIDES_ARE_NULL);
	}
}
