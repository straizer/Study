/**
 * Class representing any shape.
 */
public abstract class Shape {

	/**
	 * Prints class name.
	 */
	void print() {
		System.out.println(getClass().getName());
	}

	/**
	 * Calculates area.
	 *
	 * @return the area of a shape
	 */
	public abstract double getArea();

	/**
	 * Calculates perimeter.
	 *
	 * @return the perimeter of a shape
	 */
	public abstract double getPerimeter();
}
