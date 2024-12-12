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

/** Class representing triangle. */
@Slf4j
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ExtensionMethod(ExceptionUtils.class)
public final class Triangle extends Shape {

	/**
	 * Creates {@link Shape} representing triangle.
	 * @param x     first side length
	 * @param y     second side length
	 * @param z     third side length
	 * @param color color of the {@code Triangle}
	 */
	public Triangle(final double x, final double y, final double z, final @NonNull Color color) {
		super(new double[]{x, y, z}, color);
		if (x + y <= z || y + z <= x || x + z <= y) {
			Messages.Error.TRIANGLE_RULE().warnAndThrow(IllegalArgumentException.class);
		}
	}

	/** @throws IllegalStateException when {@code sides} are {@code null}. */
	@Transient
	@Override
	public double getArea() {
		final double halfPerimeter = getPerimeter() / 2.0;
		return traceNonNull(Math.sqrt(halfPerimeter * mapSides(
			stream -> stream.map(it -> halfPerimeter - it).reduce(1.0, DoubleReducers.MULTIPLYING))));
	}
}
