package semester5.pwjj.lab3.shapes;

import org.assertj.core.api.ThrowableAssert;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.lab3.Color;
import semester5.pwjj.lab3.shapes.impl.Rectangle;

import java.util.Locale;

@DisplayName("Rectangle Error Tests")
class RectangleErrorTests extends ShapeErrorTestsBase {

	private static final ThrowableAssert.ThrowingCallable NON_POSITIVE_SIDE_RECTANGLE =
		() -> new Rectangle(0, 1, Color.RED);

	@DisplayName("non positive side length")
	@ParameterizedTest
	@MethodSource("localeSource")
	void getPerimeterNonPositiveSideTest(@NonNull final Locale locale) {
		getPerimeterNonPositiveSideTest(locale, NON_POSITIVE_SIDE_RECTANGLE, "rectangle");
	}
}
