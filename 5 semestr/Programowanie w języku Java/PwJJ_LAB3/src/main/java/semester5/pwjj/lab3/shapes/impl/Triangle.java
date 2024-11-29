package semester5.pwjj.lab3.shapes.impl;

import semester5.pwjj.lab3.Color;
import semester5.pwjj.lab3.shapes.Shape;

/**
 * Class representing triangle.
 */
public final class Triangle extends Shape {

	private final double x;
	private final double y;
	private final double z;

	/**
	 * Creates {@link Shape} representing triangle.
	 *
	 * @param x first side length
	 * @param y second side length
	 * @param z third side length
	 * @param color color of the triangle
	 */
	public Triangle(final double x, final double y, final double z, final Color color) {
		super(color);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public double getArea() {
		final double halfPerimeter = getPerimeter() / 2.0;
		return Math.sqrt(halfPerimeter * (halfPerimeter - x) * (halfPerimeter - y) * (halfPerimeter - z));
	}

	@Override
	public double getPerimeter() {
		return x + y + z;
	}
}
