import semester5.pwjj.lab3.Color;
import semester5.pwjj.lab3.shapes.Shape;
import semester5.pwjj.lab3.shapes.ShapeDescriber;
import semester5.pwjj.lab3.shapes.impl.Rectangle;
import semester5.pwjj.lab3.shapes.impl.Triangle;

/**
 * Application entry point.
 */
public static void main() {
	final Shape[] shapes = {
		new Rectangle(1.0, 2.0, Color.RED),
		new Triangle(3.0, 4.0, 5.0, Color.GREEN)
	};

	for (final Shape shape : shapes) {
		ShapeDescriber.describe(shape);
	}
}
