package semester5.pwjj.shapes.impl;

import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.Color;
import semester5.pwjj.DoubleReducers;
import semester5.pwjj.shapes.Shape;

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
		_logCtor();
	}

	@Override
	public double getPerimeter() {
		return _log(2 * super.getPerimeter());
	}

	@Override
	public double getArea() {
		return _log(Arrays.stream(sides).reduce(1.0, DoubleReducers.MULTIPLYING));
	}

	@Override
	@NonNull
	public String _repr() {
		return String.format("Rectangle(super=(%s))", super._repr()); //NON-NLS
	}
}
