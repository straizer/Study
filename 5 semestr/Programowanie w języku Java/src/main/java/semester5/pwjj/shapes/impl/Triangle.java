package semester5.pwjj.shapes.impl;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.Color;
import semester5.pwjj.DoubleReducers;
import semester5.pwjj.i18n.Messages;
import semester5.pwjj.shapes.Shape;

import java.util.Arrays;

/**
 * Class representing triangle.
 */
@Slf4j
public final class Triangle extends Shape {
	/**
	 * Creates {@link Shape} representing triangle.
	 *
	 * @param x     first side length
	 * @param y     second side length
	 * @param z     third side length
	 * @param color color of the triangle
	 */
	public Triangle(final double x, final double y, final double z, @NonNull final Color color) {
		super(new double[]{x, y, z}, color);
		if (x + y <= z || y + z <= x || x + z <= y) {
			final String message = Messages.Error.TRIANGLE_RULE();
			log.warn(message);
			throw new IllegalArgumentException(message);
		}
		_logCtor();
	}

	@Override
	public double getArea() {
		final double halfPerimeter = getPerimeter() / 2.0;
		final double temp = Arrays.stream(sides).map(it -> halfPerimeter - it).reduce(1.0, DoubleReducers.MULTIPLYING);
		return _log(Math.sqrt(halfPerimeter * temp));
	}

	@Override
	@NonNull
	public String _repr() {
		return String.format("Triangle(super=(%s))", super._repr()); //NON-NLS
	}
}
