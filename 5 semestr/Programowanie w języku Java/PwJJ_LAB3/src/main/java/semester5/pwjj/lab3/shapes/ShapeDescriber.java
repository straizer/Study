package semester5.pwjj.lab3.shapes;

import lombok.extern.slf4j.Slf4j;
import semester5.pwjj.lab3.MessageProvider;

import java.util.Locale;

/**
 * Class for statically describing objects of type {@link Shape}.
 */
@Slf4j
public enum ShapeDescriber {
	;

	/**
	 * Prints {@link Shape} information.
	 *
	 * @param shape shape to describe
	 */
	public static void describe(final Shape shape) {
		log.info(MessageProvider.get("shape.name", Locale.of("pl")));
		log.info(MessageProvider.get("dupa.xd"));
		shape.print();
		log.info("Area: {}", shape.getArea());
		log.info("Perimeter: {}", shape.getPerimeter());
		log.info("Color: {}\n", shape.getColorDescription());
	}
}
