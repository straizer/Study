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
import org.checkerframework.checker.initialization.qual.UnknownInitialization;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.checkerframework.checker.nullness.util.NullnessUtil;
import semester5.pwjj.entities.Color;
import semester5.pwjj.utils.Traceable;
import semester5.pwjj.utils.extensions.ExceptionUtils;
import semester5.pwjj.utils.extensions.StreamUtils;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.extensions.TraceableUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * Represents an abstract geometric shape with inherent properties and behaviors.
 * This class provides a base structure for specific types of shapes while ensuring
 * certain shared functionality across all shapes, such as calculating perimeter
 * and area, and exposing shape-specific attributes like {@code sides} and {@link Color}.
 * <p>
 * Each subclass is responsible for implementing the concrete area (and optionally perimeter) calculation
 * specific to that shape.
 * <p>
 * This class is a JPA {@link Entity}.
 */
@SuppressWarnings("ClassWithoutLogger")
@Entity
@Inheritance
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@ExtensionMethod({StringUtils.class, Arrays.class, StreamUtils.class, ExceptionUtils.class, NullnessUtil.class})
public abstract class Shape implements Traceable {

	/**
	 * Represents the sides of a {@code Shape}.
	 * <p>
	 * The array holds the lengths of the sides of the {@code Shape}, where each value must be non-negative.
	 * Supports cases where the {@code sides} array may be {@code null}.
	 */
	@ElementCollection
	private final double @Nullable [] sides;

	/**
	 * Represents the {@link Color} of the {@code Shape}.
	 * <p>
	 * The {@link Color} is embedded within the class and is optional, allowing {@code null} values.
	 */
	@SuppressWarnings("UseOfConcreteClass")
	@Embedded
	@Getter
	private final @Nullable Color color;

	/**
	 * Represents the unique identifier for an entity in the database.
	 * <p>
	 * This field is automatically generated and annotated for use with JPA.
	 */
	@Id
	@GeneratedValue
	@ToString.Include(rank = 5)
	private int id;

	/**
	 * Creates a {@code Shape} object with specified {@code sides} and {@link Color}.
	 * Ensures that all {@code sides} are positive.
	 * @param sides the array containing the lengths of the sides. Each side length must be greater than zero.
	 * @param color the {@link Color} of the {@code Shape}
	 * @throws IllegalArgumentException if any side length is less than or equal to zero
	 */
	protected Shape(
		final double @NonNull [] sides, @SuppressWarnings("UseOfConcreteClass") final @NonNull Color color
	) {
		for (final double side : sides) {
			if (side <= 0) {
				throw Messages.Error.SIDES_NOT_POSITIVE(getClassNameNls())
					.warnAndReturn(IllegalArgumentException.class);
			}
		}
		this.sides = sides.clone();
		this.color = color;
		//noinspection ThisEscapedInObjectConstruction
		TraceableUtils.traceConstructor(this);
	}

	/**
	 * Converts the object into a well-formatted, human-readable, i18nized {@link String} representation.
	 * @return a {@link String} representing the {@code Shape} object
	 */
	public @NonNull String toPrettyString() {
		return trace(Messages.ToString.SHAPE(id, getClassNameNls(), getPerimeter(), getArea(), color,
			mapSides(stream -> stream.mapToObj(it -> "%.2f".safeFormat(it)).joining("; ")))); //NON-NLS
	}

	/**
	 * Calculates and returns the perimeter of a specific {@code Shape}.
	 * <p>
	 * Perimeter of a {@code Shape} is calculated by summing up its side lengths.
	 * It may be overridden by a more specific implementation.
	 * @return a perimeter as a {@code double}
	 * @throws IllegalStateException if {@code sides} are {@code null}
	 */
	public double getPerimeter() {
		return trace(mapSides(DoubleStream::sum));
	}

	/**
	 * Calculates and returns the area of a specific {@code Shape}.
	 * This method is abstract and must be implemented by a subclass to provide the appropriate formula.
	 * @return an area as a {@code double}, calculated based on the specific implementation in a subclass
	 * @throws IllegalStateException if {@code sides} are {@code null}
	 */
	public abstract double getArea();

	/**
	 * Applies a given mapping {@link Function} to the {@link Stream} of {@code sides} associated with this object.
	 * @param mapper the {@link Function} to be applied to the {@link Stream} of {@code sides}
	 * @param <T>    the type of the result returned by the {@code mapper}
	 * @return a result of applying the {@code mapper} function to the {@link Stream} of {@code sides}
	 * @throws IllegalStateException if the {@code sides} are {@code null}
	 */
	protected <@Nullable T> @PolyNull T mapSides(
		final @NonNull Function<? super @NonNull DoubleStream, @PolyNull T> mapper
	) {
		if (Objects.isNull(sides)) {
			throw Messages.Error.SIDES_ARE_NULL(getClassNameNls()).warnAndReturn(IllegalStateException.class);
		}
		return mapper.apply(sides.castNonNull().stream());
	}

	/**
	 * Retrieves the i18nized class name based on the class' simple name and a predefined naming convention.
	 * @return a i18nized {@link String} representation of the class name.
	 */
	private @NonNull String getClassNameNls(@UnknownInitialization Shape this) {
		//noinspection StringConcatenation
		final @NonNull String propertyName = "name." + getClass().getSimpleName().toLowerCase(Locale.ENGLISH); //NON-NLS
		return new Messages.EntitiesShapesI18nProperty(propertyName).getMessage();
	}
}
