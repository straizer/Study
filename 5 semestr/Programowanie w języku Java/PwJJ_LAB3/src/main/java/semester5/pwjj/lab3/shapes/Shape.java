package semester5.pwjj.lab3.shapes;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.lab3.Color;
import semester5.pwjj.lab3.i18n.MessageProvider;
import semester5.pwjj.lab3.i18n.Messages;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Class representing any shape.
 */
@Slf4j
public abstract class Shape {

	protected final double[] sides;

	@Getter
	@NonNull
	private final Color color;

	protected Shape(final double[] sides, @NonNull final Color color) {
		for (final double side : sides) {
			if (side <= 0) {
				final String message = Messages.Error.SIDES_NOT_POSITIVE(getClassNameNls());
				log.warn(message);
				throw new IllegalArgumentException(message);
			}
		}
		this.sides = sides.clone();
		this.color = color;
	}

	/**
	 * Calculates perimeter.
	 *
	 * @return the perimeter of a shape
	 */
	public double getPerimeter() {
		return Arrays.stream(sides).sum();
	}

	/**
	 * Calculates area.
	 *
	 * @return the area of a shape
	 */
	public abstract double getArea();

	/**
	 * Creates human-readable description.
	 *
	 * @return description of the current object
	 */
	@Override
	@NonNull
	public String toString() {
		return Messages.ToString.SHAPE(getClassNameNls(), getPerimeter(), getArea(), color,
			Arrays.stream(sides)
				.mapToObj(it -> String.format("%.2f", it))
				.collect(Collectors.joining("; ", "[", "]")));
	}

	@NonNull
	private String getClassNameNls() {
		return MessageProvider.get(
			new Messages.I18nProperty("name." + getClass().getSimpleName().toLowerCase())); //NON-NLS
	}
}
