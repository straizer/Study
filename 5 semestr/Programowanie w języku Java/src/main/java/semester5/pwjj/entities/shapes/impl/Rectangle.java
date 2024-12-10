package semester5.pwjj.entities.shapes.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.entities.Color;
import semester5.pwjj.entities.shapes.Shape;
import semester5.pwjj.utils.DoubleReducers;

/** Class representing rectangle. */
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
public final class Rectangle extends Shape {

	/**
	 * Creates {@link Shape} representing rectangle.
	 * @param x     first side length
	 * @param y     second side length
	 * @param color color of the {@code Rectangle}
	 */
	public Rectangle(final double x, final double y, final @NonNull Color color) {
		super(new double[]{x, y}, color);
		traceCtor();
	}

	/** @throws IllegalStateException if {@code sides} are {@code null}. */
	@Transient
	@Override
	public double getPerimeter() {
		return traceNonNull(2 * super.getPerimeter());
	}

	/** @throws IllegalStateException if {@code sides} are {@code null}. */
	@Transient
	@Override
	public double getArea() {
		return traceNonNull(mapSides(stream -> stream.reduce(1.0, DoubleReducers.MULTIPLYING)));
	}
}
