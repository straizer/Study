package semester5.pwjj.entities.shapes.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import semester5.pwjj.entities.Color;
import semester5.pwjj.entities.shapes.Shape;
import semester5.pwjj.utils.DoubleReducers;

/**
 * Represents a rectangle as a concrete implementation of the {@link Shape}.
 * This class provides functionality to calculate the area and perimeter of a rectangle
 * based on its two side lengths and associated {@link Color}.
 * <p>
 * Ensures that the side lengths are positive during instantiation.
 */
@SuppressWarnings("ClassWithoutLogger")
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Rectangle extends Shape {

	/**
	 * Creates a {@code Rectangle} object with specified side lengths and {@link Color}.
	 * @param x     the length of the sides of the {@code Rectangle} perpendicular to {@code y}
	 * @param y     the length of the sides of the {@code Rectangle} perpendicular to {@code x}
	 * @param color the {@link Color} of the {@code Rectangle}
	 * @throws IllegalArgumentException if any side length is less than or equal to zero
	 */
	public Rectangle(final double x, final double y, @SuppressWarnings("UseOfConcreteClass") final Color color) {
		super(new double[]{x, y}, color);
	}

	/**
	 * Calculates and returns the perimeter of a {@code Rectangle}.
	 * @return a perimeter as a {@code double}
	 * @throws IllegalStateException if {@code sides} are {@code null}
	 */
	@Transient
	@Override
	public double getPerimeter() {
		return trace(2 * super.getPerimeter());
	}

	/**
	 * Calculates and returns the area of a {@code Rectangle}.
	 * @return an area as a {@code double}
	 * @throws IllegalStateException if {@code sides} are {@code null}
	 */
	@Transient
	@Override
	public double getArea() {
		return trace(mapSides(stream -> stream.reduce(1.0, DoubleReducers.MULTIPLYING)));
	}
}
