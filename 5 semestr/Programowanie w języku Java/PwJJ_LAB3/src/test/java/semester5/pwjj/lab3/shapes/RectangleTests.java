package semester5.pwjj.lab3.shapes;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import semester5.pwjj.lab3.Color;
import semester5.pwjj.lab3.TestsBase;
import semester5.pwjj.lab3.shapes.impl.Rectangle;

@DisplayName("Rectangle Tests")
final class RectangleTests extends TestsBase {

	private static final Shape uut = new Rectangle(1.0, 2.0, Color.RED);

	@DisplayName("get perimeter")
	@Test
	void getPerimeterTest() {
		Assertions.assertThat(uut.getPerimeter()).isEqualTo(6.0);
	}

	@DisplayName("get area")
	@Test
	void getAreaTest() {
		Assertions.assertThat(uut.getArea()).isEqualTo(2.0);
	}

	@DisplayName("get color")
	@Test
	void getColorTest() {
		Assertions.assertThat(uut.getColor()).isEqualTo(Color.RED);
	}

	@DisplayName("to string")
	@Test
	void toStringTest() {
		Assertions.assertThat(uut.toString()).isEqualTo(TO_STRING_SHAPE);
	}
}
