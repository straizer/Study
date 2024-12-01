/**
 * Class representing rectangle.
 */
final class Rectangle extends Shape {

	private final double x;
	private final double y;

	/**
	 * Creates {@link Shape} representing rectangle.
	 *
	 * @param x first side length
	 * @param y second side length
	 */
	Rectangle(final double x, final double y) {
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
