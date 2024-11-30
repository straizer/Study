package semester5.pwjj.lab3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import semester5.pwjj.lab3.shapes.impl.Rectangle;

import static org.assertj.core.api.Assertions.assertThat;

public class RectangleTests {

	private Rectangle uut;

	@BeforeEach
	void setup() {
		uut = new Rectangle(1.0, 2.0, Color.RED);
	}

	@Test
	@DisplayName("get perimeter")
	public void getPerimeterTest() {
		// given
		final double expected = 6.0;

		// when
		final double actual = uut.getPerimeter();

		//then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@DisplayName("get area")
	public void getAreaTest() {
		// given
		final double expected = 2.0;

		// when
		final double actual = uut.getArea();

		//then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@DisplayName("get color description")
	public void getColorDescriptionTest() {
		// given
		final String expected = Color.RED.toString();

		// when
		final String actual = uut.getColorDescription();

		//then
		assertThat(actual).isEqualTo(expected);
	}
}
