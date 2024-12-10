package semester5.pwjj.entities.shapes;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.Representative;
import semester5.pwjj.entities.Color;
import semester5.pwjj.utils.NullableUtils;
import semester5.pwjj.utils.StringUtils;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/** Class representing any shape. */
@Slf4j
@Entity
@Inheritance
@NoArgsConstructor
@ToString
public abstract class Shape implements Representative {

	@Id
	@GeneratedValue
	private int id;

	@ElementCollection
	private double @Nullable [] sides;

	@Embedded
	@Getter
	private @Nullable Color color;

	/**
	 * Creates {@code Shape}.
	 * @param sides sides of the {@code Shape}
	 * @param color color of the {@code Shape}
	 * @throws IllegalArgumentException when any side of the {@code Shape} is not positive.
	 */
	protected Shape(final double @NonNull [] sides, final @NonNull Color color) {
		for (final double side : sides) {
			if (side <= 0) {
				final @NonNull String message = Messages.Error.SIDES_NOT_POSITIVE(getClassNameNonNls());
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
		return traceNonNull(Messages.ToString.SHAPE(id, getClassNameNonNls(), getPerimeter(), getArea(), color,
			mapSides(stream -> stream
				.mapToObj(it -> StringUtils.format("%.2f", it)) //NON-NLS
				.collect(Collectors.joining("; ", "[", "]")))));
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
		if (Objects.isNull(sides)) {
			final @NonNull String message = Messages.Error.SIDES_ARE_NULL(getClassNameNonNls());
			log.warn(message);
			throw new IllegalStateException(message);
		}
		return Objects.requireNonNull(NullableUtils.mapOrNull(sides, it -> mapper.apply(Arrays.stream(it))));
	}

	/**
	 * Extracts non-nationalized class name.
	 * @return class name
	 */
	private @NonNull String getClassNameNonNls() {
		final @NonNull String propertyName = "name." + getClass().getSimpleName().toLowerCase(Locale.ENGLISH); //NON-NLS
		return MessageProvider.get(new Messages.EntitiesShapesI18nProperty(propertyName));
	}
}
