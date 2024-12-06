package semester5.pwjj.shapes;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import semester5.pwjj.Color;
import semester5.pwjj.TestsBase;
import semester5.pwjj.shapes.impl.Triangle;

@DisplayName("Triangle Tests")
final class TriangleTests extends TestsBase {

	private static final Shape uut = new Triangle(3.0, 4.0, 5.0, Color.RED);

	@DisplayName("get perimeter")
	@Test
	void getPerimeterTest() {
		Assertions.assertThat(uut.getPerimeter()).isEqualTo(12.0);
	}

	@DisplayName("get area")
	@Test
	void getAreaTest() {
		Assertions.assertThat(uut.getArea()).isEqualTo(6.0);
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
