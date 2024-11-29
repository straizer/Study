package semester5.pwjj.lab3.shapes.impl;

import semester5.pwjj.lab3.Color;
import semester5.pwjj.lab3.shapes.Shape;

/**
 * Class representing rectangle.
 */
public final class Rectangle extends Shape {

	private final double x;
	private final double y;

	/**
	 * Creates {@link Shape} representing rectangle.
	 *
	 * @param x first side length
	 * @param y second side length
	 * @param color color of the rectangle
	 */
	public Rectangle(final double x, final double y, final Color color) {
		super(color);
		this.x = x;
		this.y = y;
	}

	@Override
	public double getArea() {
		return x * y;
	}

	@Override
	public double getPerimeter() {
		return 2.0 * (x + y);
	}
}
