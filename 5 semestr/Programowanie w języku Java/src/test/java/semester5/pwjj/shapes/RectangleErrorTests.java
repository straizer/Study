package semester5.pwjj.shapes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import semester5.pwjj.Color;
import semester5.pwjj.ErrorTestsBase;
import semester5.pwjj.shapes.impl.Rectangle;

@DisplayName("Rectangle Error Tests")
class RectangleErrorTests extends ErrorTestsBase {

	@DisplayName("non positive side length")
	@Test
	void getPerimeterNonPositiveSideTest() {
		throwsIllegalArgumentException(() -> new Rectangle(0, 1, Color.RED), ERROR_SIDES_NOT_POSITIVE);
	}
}
