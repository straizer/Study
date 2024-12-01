package semester5.pwjj.lab3.shapes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import semester5.pwjj.lab3.Color;
import semester5.pwjj.lab3.Messages;

/**
 * Class representing any shape.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class Shape {

	private final Color color;

	/**
	 * Creates human-readable description.
	 *
	 * @return description of the current object
	 */
	@Override
	public String toString() {
		return String.format(
			Messages.get("toString.shape"),
			Messages.get("name." + getClass().getSimpleName().toLowerCase()),
			getPerimeter(),
			getArea(),
			color.toString());
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
