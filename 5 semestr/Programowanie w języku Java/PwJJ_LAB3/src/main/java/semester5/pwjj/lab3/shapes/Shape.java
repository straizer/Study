package semester5.pwjj.lab3.shapes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import semester5.pwjj.lab3.Color;

/**
 * Class representing any shape.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class Shape {

	private final Color color;

	/**
	 * Prints class name.
	 */
	public final void print() {
		log.info(getClass().getSimpleName());
	}

	/**
	 * Describes color in human-readable form.
	 *
	 * @return text description of the color
	 */
	public final String getColorDescription() {
		new Thread(() -> log.info("TEST")).start();
		final String rawColorDescription = color.toString();
		return rawColorDescription.substring(rawColorDescription.indexOf('[') + 1, rawColorDescription.lastIndexOf(']'));
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
