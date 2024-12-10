package semester5.pwjj.entities.shapes.impl;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import semester5.pwjj.TestsBase;
import semester5.pwjj.entities.Color;
import semester5.pwjj.entities.shapes.Shape;
import semester5.pwjj.utils.i18n.MessageProvider;

import static org.mockito.Mockito.times;

@DisplayName("Rectangle Tests")
final class RectangleTests extends TestsBase {

	private static final @NonNull Shape uut = new Rectangle(1.0, 2.0, Color.RED);

	@DisplayName("read perimeter")
	@Test
	void getPerimeterTest() {
		Assertions.assertThat(uut.getPerimeter()).isEqualTo(6.0);
	}

	@DisplayName("read area")
	@Test
	void getAreaTest() {
		Assertions.assertThat(uut.getArea()).isEqualTo(2.0);
	}

	@DisplayName("read color")
	@Test
	void getColorTest() {
		Assertions.assertThat(uut.getColor()).isEqualTo(Color.RED);
	}

	@DisplayName("to pretty string")
	@Test
	void toPrettyStringTest() {
		Assertions.assertThat(uut.toPrettyString()).isEqualTo(ENTITIES_SHAPES_TO_STRING_SHAPE.getPropertyName());
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_SHAPES_NAME_RECTANGLE), times(1));
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_TO_STRING_COLOR), times(1));
		messageProviderMock.verify(() -> MessageProvider.get(ENTITIES_SHAPES_TO_STRING_SHAPE), times(1));
	}
}
