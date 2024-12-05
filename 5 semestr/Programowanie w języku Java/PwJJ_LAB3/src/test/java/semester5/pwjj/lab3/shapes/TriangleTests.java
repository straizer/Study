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
import semester5.pwjj.lab3.shapes.impl.Triangle;

import java.util.Locale;
import java.util.stream.Stream;

@DisplayName("Triangle Tests")
final class TriangleTests extends TestsBase {

	private static final Shape uut = new Triangle(3.0, 4.0, 5.0, Color.RED);

	private static Stream<Arguments> toStringTest() {
		return Stream.of(
			Arguments.of(Locale.of("pl"), """
				
				Typ kształtu: trójkąt
				Obwód: 12,00
				Powierzchnia: 6,00
				Kolor: czerwony: 255, zielony: 0, niebieski: 0, przezroczystość: 0
				Boki: [3,00; 4,00; 5,00]"""),
			Arguments.of(Locale.ENGLISH, """
				
				Shape type: triangle
				Perimeter: 12.00
				Area: 6.00
				Color: red: 255, green: 0, blue: 0, alpha: 0
				Sides: [3.00; 4.00; 5.00]"""));
	}

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
	@ParameterizedTest(name = "[{index}] {0}")
	@MethodSource
	void toStringTest(@NonNull final Locale locale, @NonNull final String expected) {
		Locale.setDefault(locale);
		Assertions.assertThat(uut.toString()).isEqualTo(expected);
	}
}
