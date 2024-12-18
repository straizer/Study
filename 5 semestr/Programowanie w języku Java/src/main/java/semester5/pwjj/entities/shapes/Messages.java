package semester5.pwjj.entities.shapes;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.entities.Color;
import semester5.pwjj.utils.extensions.NullableUtils;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.extensions.TraceableUtils;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code entities.shapes} in the current {@link Locale}.
 * @implNote If different translation is required without changing default {@link Locale},
 * use {@link MessageProvider#get(I18nProperty, Locale)}
 */
@SuppressWarnings({"PublicInnerClass", "StaticMethodOnlyUsedInOneClass"})
@ExtensionMethod({StringUtils.class, TraceableUtils.class, NullableUtils.class})
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
		 * @param shapeName the name of the {@link Shape}
		 * @return the formatted value of key {@code entities.shapes.error.sidesAreNull}
		 */
		public static @NonNull String SIDES_ARE_NULL(final @NonNull String shapeName) {
			return SIDES_ARE_NULL.getMessage().safeFormat(shapeName).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code entities.shapes.error.sidesNotPositive}.
		 * @param shapeName the name of the {@link Shape}
		 * @return the formatted value of key {@code entities.shapes.error.sidesNotPositive}
		 */
		public static @NonNull String SIDES_NOT_POSITIVE(final @NonNull String shapeName) {
			return SIDES_NOT_POSITIVE.getMessage().safeFormat(shapeName).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code entities.shapes.error.triangleRule}.
		 * @return the value of key {@code entities.shapes.error.triangleRule}
		 */
		public static @NonNull String TRIANGLE_RULE() {
			return TRIANGLE_RULE.getMessage().trace(Error.class);
		}
	}

	/** I18n keys and value retrievers for {@code entities.shapes.toString} messages. */
	public enum ToString {
		;

		/** I18n key with value {@code entities.shapes.toString.shape}. */
		public static final @NonNull I18nProperty SHAPE = new EntitiesShapesI18nProperty("toString.shape");

		/**
		 * I18n value retriever for key {@code entities.shapes.toString.shape}.
		 * @param id        the ID of the {@link Shape}
		 * @param shapeName the name of the {@link Shape}
		 * @param perimeter the perimeter of the {@link Shape}
		 * @param area      the area of the {@link Shape}
		 * @param color     the color of the {@link Shape}
		 * @param sides     the {@link String} representation of sides of the {@link Shape}
		 * @return the formatted value of key {@code entities.shapes.toString.shape}
		 */
		@SuppressWarnings("MethodWithTooManyParameters")
		public static @NonNull String SHAPE(
			final int id, final @Nullable String shapeName, final double perimeter, final double area,
			@SuppressWarnings("UseOfConcreteClass") final @Nullable Color color, final @Nullable String sides
		) {
			return SHAPE.getMessage()
				.safeFormat(id, shapeName, perimeter, area, color.mapOrNull(Color::toPrettyString), sides)
				.trace(ToString.class);
		}
	}

	/**
	 * Utility class representing an i18n property specifically for the {@code entities.shapes} namespace.
	 * This class extends the {@link I18nProperty} class, allowing for the creation of specialized property keys by
	 * automatically prefixing them with "{@code entities.shapes.}".
	 */
	public static class EntitiesShapesI18nProperty extends I18nProperty {

		/**
		 * Constructs an instance of {@code EntitiesShapesI18nProperty} with a specific i18n property name.
		 * Prepends the property name with "{@code entities.shapes.}" to create the full property key.
		 * @param propertyName the name of the i18n property, which will be prefixed with "{@code entities.shapes.}"
		 */
		@SuppressWarnings("StringConcatenation")
		public EntitiesShapesI18nProperty(final @NonNull String propertyName) {
			super("entities.shapes." + propertyName); //NON-NLS
		}
	}
}
