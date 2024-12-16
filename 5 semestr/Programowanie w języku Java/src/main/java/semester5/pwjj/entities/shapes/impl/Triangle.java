package semester5.pwjj.entities.shapes.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.entities.Color;
import semester5.pwjj.entities.shapes.Messages;
import semester5.pwjj.entities.shapes.Shape;
import semester5.pwjj.utils.DoubleReducers;
import semester5.pwjj.utils.extensions.ExceptionUtils;

/**
 * Represents a triangle as a concrete implementation of the {@link Shape}.
 * This class provides functionality to calculate the area and perimeter of a triangle
 * based on its three side lengths and associated {@link Color}.
 * Ensures that the side lengths are positive during instantiation and the properties of a valid triangle are satisfied,
 * such as adhering to the triangle inequality rule.
 */
@Slf4j
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ExtensionMethod(ExceptionUtils.class)
public final class Triangle extends Shape {

	/**
	 * Creates a {@code Rectangle} object with specified side lengths and {@link Color}.
	 * Validates that the given side lengths satisfy the triangle inequality rule.
	 * @param x     the length of the first side of the {@code Triangle}
	 * @param y     the length of the second side of the {@code Triangle}
	 * @param z     the length of the third side of the {@code Triangle}
	 * @param color the {@link Color} of the {@code Triangle}
	 * @throws IllegalArgumentException if any side length is less than or equal to zero,
	 *                                  or if the side lengths don't satisfy the triangle inequality rule
	 */
	public Triangle(
		final double x, final double y, final double z,
		@SuppressWarnings("UseOfConcreteClass") final @NonNull Color color
	) {
		super(new double[]{x, y, z}, color);
		if (x + y <= z || y + z <= x || x + z <= y) {
			Messages.Error.TRIANGLE_RULE().warnAndThrow(IllegalArgumentException.class);
		}
	}

	/**
	 * Calculates and returns the area of a {@code Triangle}.
	 * @return an area as a {@code double}
	 * @throws IllegalStateException if {@code sides} are {@code null}
	 */
	@Transient
	@Override
	public double getArea() {
		final double halfPerimeter = getPerimeter() / 2.0;
		return trace(Math.sqrt(halfPerimeter * mapSides(
			stream -> stream.map(it -> halfPerimeter - it).reduce(1.0, DoubleReducers.MULTIPLYING))));
	}
}
