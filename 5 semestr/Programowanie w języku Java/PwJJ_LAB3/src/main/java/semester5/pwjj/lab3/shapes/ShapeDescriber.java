package semester5.pwjj.lab3.shapes;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import semester5.pwjj.lab3.Messages;

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
		Messages.log("shape.type", shape.getType());
		Messages.log("shape.area", shape.getArea());
		Messages.log("shape.perimeter", shape.getPerimeter());
		Messages.log("shape.color", shape.getColorDescription());
		log.info(StringUtils.EMPTY);
	}
}
