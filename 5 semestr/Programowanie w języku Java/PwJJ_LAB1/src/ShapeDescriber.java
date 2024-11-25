/**
 * Class for statically describing objects of type {@code Shape}.
 */
enum ShapeDescriber {
	;

	/**
	 * Prints {@code shape} information.
	 *
	 * @param shape shape to describe
	 */
	static void describe(final Shape shape) {
		System.out.print("Shape name: ");
		shape.print();
		System.out.println("Area: " + shape.getArea());
		System.out.println("Perimeter: " + shape.getPerimeter() + '\n');
	}
}
