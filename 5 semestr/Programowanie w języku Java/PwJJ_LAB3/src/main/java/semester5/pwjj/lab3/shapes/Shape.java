package semester5.pwjj.lab3.shapes;

import lombok.RequiredArgsConstructor;
import semester5.pwjj.lab3.Color;

/**
 * Class representing any shape.
 */
@RequiredArgsConstructor
public abstract class Shape {

	private final Color color;

	/**
	 * Returns class name.
	 *
	 * @return instance class name
	 */
	public final String getType() {
		return getClass().getSimpleName();
	}

	/**
	 * Describes color in human-readable form.
	 *
	 * @return text description of the color
	 */
	public final String getColorDescription() {
		return color.toString();
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
