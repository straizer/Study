package semester5.pwjj.entities.shapes;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.entities.Color;
import semester5.pwjj.utils.NullableUtils;
import semester5.pwjj.utils.RepresentativeUtils;
import semester5.pwjj.utils.StringUtils;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code entities.shapes} in the current locale.
 * @implNote If different translation is required without changing default locale,
 * use {@link MessageProvider#get(I18nProperty i18nProperty, Locale locale)}
 */
@ExtensionMethod({NullableUtils.class, StringUtils.class, RepresentativeUtils.class})
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code entities.shapes.error} messages. */
	public enum Error {
		;

		/** I18n key with value {@code entities.shapes.error.sidesAreNull}. */
		public static final @NonNull I18nProperty SIDES_ARE_NULL =
			new EntitiesShapesI18nProperty("error.sidesAreNull");

		/** I18n key with value {@code entities.shapes.error.sidesNotPositive}. */
		public static final @NonNull I18nProperty SIDES_NOT_POSITIVE =
			new EntitiesShapesI18nProperty("error.sidesNotPositive");

		/** I18n key with value {@code entities.shapes.error.triangleRule}. */
		public static final @NonNull I18nProperty TRIANGLE_RULE =
			new EntitiesShapesI18nProperty("error.triangleRule");

		/**
		 * I18n value retriever for key {@code entities.shapes.error.sidesAreNull}.
		 * @param shapeName name of the {@link Shape}
		 * @return value of key {@code entities.shapes.error.sidesAreNull}
		 */
		public static @NonNull String SIDES_ARE_NULL(final @Nullable String shapeName) {
			return SIDES_ARE_NULL.getMessage().safeFormat(shapeName).traceNonNull(Error.class);
		}

		/**
		 * I18n value retriever for key {@code entities.shapes.error.sidesNotPositive}.
		 * @param shapeName name of the {@link Shape}
		 * @return value of key {@code entities.shapes.error.sidesNotPositive}
		 */
		public static @NonNull String SIDES_NOT_POSITIVE(final @Nullable String shapeName) {
			return SIDES_NOT_POSITIVE.getMessage().safeFormat(shapeName).traceNonNull(Error.class);
		}

		/**
		 * I18n value retriever for key {@code entities.shapes.error.triangleRule}.
		 * @return value of key {@code entities.shapes.error.triangleRule}
		 */
		public static @NonNull String TRIANGLE_RULE() {
			return TRIANGLE_RULE.getMessage().traceNonNull(Error.class);
		}
	}

	/** I18n keys and value retrievers for {@code entities.shapes.name} messages. */
	public enum Name {
		;

		/** I18n key with value {@code entities.shapes.name.rectangle}. */
		public static final @NonNull I18nProperty RECTANGLE = new EntitiesShapesI18nProperty("name.rectangle");

		/** I18n key with value {@code entities.shapes.name.triangle}. */
		public static final @NonNull I18nProperty TRIANGLE = new EntitiesShapesI18nProperty("name.triangle");

		/**
		 * I18n value retriever for key {@code entities.shapes.name.rectangle}.
		 * @return value of key {@code entities.shapes.name.rectangle}
		 */
		public static @NonNull String RECTANGLE() {
			return RECTANGLE.getMessage().traceNonNull(Name.class);
		}

		/**
		 * I18n value retriever for key {@code entities.shapes.name.triangle}.
		 * @return value of key {@code entities.shapes.name.triangle}
		 */
		public static @NonNull String TRIANGLE() {
			return TRIANGLE.getMessage().traceNonNull(Name.class);
		}
	}

	/** I18n keys and value retrievers for {@code entities.shapes.toString} messages. */
	public enum ToString {
		;

		/** I18n key with value {@code entities.shapes.toString.shape}. */
		public static final @NonNull I18nProperty SHAPE = new EntitiesShapesI18nProperty("toString.shape");

		/**
		 * I18n value retriever for key {@code entities.shapes.toString.shape}.
		 * @param id        id of the {@link Shape}
		 * @param shapeName name of the {@link Shape}
		 * @param perimeter perimeter of the {@link Shape}
		 * @param area      area of the {@link Shape}
		 * @param color     color of the {@link Shape}
		 * @param sides     {@link String} representation of sides of the {@link Shape}
		 * @return value of key {@code entities.shapes.toString.shape}
		 */
		public static @NonNull String SHAPE(
			final int id, final @Nullable String shapeName, final double perimeter, final double area,
			final @Nullable Color color, final @Nullable String sides
		) {
			return SHAPE.getMessage()
				.safeFormat(id, shapeName, perimeter, area, color.mapOrNull(Color::toPrettyString), sides)
				.traceNonNull(ToString.class);
		}
	}


	/** Class storing i18n property constants for package {@code entities.shapes}. */
	public static class EntitiesShapesI18nProperty extends I18nProperty {

		/**
		 * Creates an object of type {@code I18nProperty}.
		 * @param propertyName name of I18n property
		 */
		public EntitiesShapesI18nProperty(@NonNull final String propertyName) {
			super("entities.shapes." + propertyName); //NON-NLS
		}
	}
}
