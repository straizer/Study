package semester5.pwjj.entities.shapes.impl;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.ErrorTestsBase;
import semester5.pwjj.entities.Color;

@DisplayName("Rectangle Error Tests")
final class RectangleErrorTests extends EntitiesShapesImplErrorTestsBase {

	/** Method executed once before all tests. */
	@BeforeAll
	static void beforeAll() {
		emptyShape = new Rectangle();
	}

	@DisplayName("constructor non positive side")
	@Test
	void constructorNonPositiveSideTest() {
		ErrorTestsBase.throwsIllegalArgumentException(
			() -> new Rectangle(0, 1, Color.RED), ERROR_SIDES_NOT_POSITIVE.getPropertyName());
		verifyMessageProviderMockWasUsedFor(NAME_RECTANGLE);
		verifyMessageProviderMockWasUsedFor(ERROR_SIDES_NOT_POSITIVE);
	}

	@DisplayName("no sides")
	@ParameterizedTest
	@MethodSource
	void noSidesTest(final ThrowingCallable callable) {
		ErrorTestsBase.throwsIllegalStateException(callable, ERROR_SIDES_ARE_NULL.getPropertyName());
		verifyMessageProviderMockWasUsedFor(NAME_RECTANGLE);
		verifyMessageProviderMockWasUsedFor(ERROR_SIDES_ARE_NULL);
	}
}
