package semester5.pwjj.lab3.shapes.impl;

import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.lab3.Color;
import semester5.pwjj.lab3.DoubleReducers;
import semester5.pwjj.lab3.shapes.Shape;

import java.util.Arrays;

/**
 * Class representing rectangle.
 */
public final class Rectangle extends Shape {

	/**
	 * Creates {@link Shape} representing rectangle.
	 *
	 * @param x     first side length
	 * @param y     second side length
	 * @param color color of the rectangle
	 */
	public Rectangle(final double x, final double y, @NonNull final Color color) {
		super(new double[]{x, y}, color);
	}

	@Override
	public double getPerimeter() {
		return 2 * super.getPerimeter();
	}

	@Override
	public double getArea() {
		return Arrays.stream(sides).reduce(1.0, DoubleReducers.MULTIPLYING);
	}
}
