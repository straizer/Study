package semester5.pwjj.lab3.shapes;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.lab3.Color;
import semester5.pwjj.lab3.TestsBase;
import semester5.pwjj.lab3.shapes.impl.Rectangle;

import java.util.Locale;
import java.util.stream.Stream;

@DisplayName("Rectangle Tests")
final class RectangleTests extends TestsBase {

	private static final Shape uut = new Rectangle(1.0, 2.0, Color.RED);

	private static Stream<Arguments> toStringTest() {
		return Stream.of(
			Arguments.of(Locale.of("pl"), """
				
				Typ kształtu: kwadrat
				Obwód: 6,00
				Powierzchnia: 2,00
				Kolor: czerwony: 255, zielony: 0, niebieski: 0, przezroczystość: 0
				Boki: [1,00; 2,00]"""),
			Arguments.of(Locale.ENGLISH, """
				
				Shape type: rectangle
				Perimeter: 6.00
				Area: 2.00
				Color: red: 255, green: 0, blue: 0, alpha: 0
				Sides: [1.00; 2.00]"""));
	}

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
	@ParameterizedTest(name = "[{index}] {0}")
	@MethodSource
	void toStringTest(@NonNull final Locale locale, @NonNull final String expected) {
		Locale.setDefault(locale);
		Assertions.assertThat(uut.toString()).isEqualTo(expected);
	}
}
