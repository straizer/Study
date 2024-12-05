package semester5.pwjj.lab3.shapes;

import org.assertj.core.api.ThrowableAssert;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.lab3.Color;
import semester5.pwjj.lab3.i18n.Messages;
import semester5.pwjj.lab3.shapes.impl.Triangle;

import java.util.Locale;

@DisplayName("Triangle Error Tests")
class TriangleErrorTests extends ShapeErrorTestsBase {

	private static final ThrowableAssert.ThrowingCallable NON_POSITIVE_SIDE_TRIANGLE =
		() -> new Triangle(0, 1, 1, Color.RED);
	private static final ThrowableAssert.ThrowingCallable TRIANGLE_RULE_NOT_FULFILLED_TRIANGLE =
		() -> new Triangle(1, 2, 3, Color.RED);

	@DisplayName("non positive side length")
	@ParameterizedTest
	@MethodSource("semester5.pwjj.lab3.ErrorTestsBase#localeSource")
	void getPerimeterNonPositiveSideTest(@NonNull final Locale locale) {
		getPerimeterNonPositiveSideTest(locale, NON_POSITIVE_SIDE_TRIANGLE, "triangle");
	}

	@DisplayName("triangle rule isn't fulfilled")
	@ParameterizedTest
	@MethodSource("semester5.pwjj.lab3.ErrorTestsBase#localeSource")
	void getPerimeterTriangleRuleNotFulfilledTest(@NonNull final Locale locale) {
		throwsIllegalArgumentException(locale, TRIANGLE_RULE_NOT_FULFILLED_TRIANGLE, Messages.Error::TRIANGLE_RULE);
	}
}
