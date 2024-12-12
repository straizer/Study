package semester5.pwjj.entities.shapes;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.Representative;
import semester5.pwjj.entities.Color;
import semester5.pwjj.utils.StreamUtils;
import semester5.pwjj.utils.StringUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.DoubleStream;

/** Class representing any shape. */
@Slf4j
@Entity
@Inheritance
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@ExtensionMethod({StringUtils.class, Arrays.class, Objects.class, StreamUtils.class, ExceptionUtils.class})
public abstract class Shape implements Representative {

	@Id
	@GeneratedValue
	private int id;

	@ElementCollection
	private final double @Nullable [] sides;

	@Embedded
	@Getter
	private final @Nullable Color color;

	/**
	 * Creates {@code Shape}.
	 * @param sides sides of the {@code Shape}
	 * @param color color of the {@code Shape}
	 * @throws IllegalArgumentException when any side of the {@code Shape} is not positive.
	 */
	protected Shape(final double @NonNull [] sides, final @NonNull Color color) {
		for (final double side : sides) {
			if (side <= 0) {
				final @NonNull String message = Messages.Error.SIDES_NOT_POSITIVE(getClassNameNls());
				log.warn(message);
				throw new IllegalArgumentException(message);
			}
		}
		this.sides = sides.clone();
		this.color = color;
		traceCtor();
	}

	/**
	 * Creates human-readable description.
	 * @return description of the current object
	 */
	public @NonNull String toPrettyString() {
		return traceNonNull(Messages.ToString.SHAPE(id, getClassNameNls(), getPerimeter(), getArea(), color,
			mapSides(stream -> stream.mapToObj(it -> "%.2f".safeFormat(it)).joining("; ", "[", "]")))); //NON-NLS
	}

	/**
	 * Calculates perimeter.
	 * @return the perimeter of a {@code Shape}
	 * @throws IllegalStateException if {@code sides} are {@code null}.
	 */
	public double getPerimeter() {
		return traceNonNull(mapSides(DoubleStream::sum));
	}

	/**
	 * Calculates area.
	 * @return the area of a {@code Shape}
	 */
	public abstract double getArea();

	/**
	 * Maps {@code sides} using given {@code mapper}
	 * @param mapper mapper to apply on {@code sides}
	 * @param <T>    return type
	 * @return mapped {@code sides}
	 * @throws IllegalStateException if {@code sides} are {@code null}.
	 */
	protected <T> @NonNull T mapSides(final @NonNull Function<? super DoubleStream, T> mapper) {
			final @NonNull String message = Messages.Error.SIDES_ARE_NULL(getClassNameNls());
			log.warn(message);
			throw new IllegalStateException(message);
		if (sides.isNull()) {
		}
		return mapper.apply(sides.stream());
	}

	/**
	 * Extracts non-nationalized class name.
	 * @return class name
	 */
	private @NonNull String getClassNameNls() {
		final @NonNull String propertyName = "name." + getClass().getSimpleName().toLowerCase(Locale.ENGLISH); //NON-NLS
		return new Messages.EntitiesShapesI18nProperty(propertyName).getMessage();
	}
}
