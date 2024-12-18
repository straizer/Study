package semester5.pwjj.entities.shapes.impl;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.utils.extensions.TraceableUtils;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code entities.shapes.impl} in the current {@link Locale}.
 * @implNote If different translation is required without changing default {@link Locale},
 * use {@link MessageProvider#get(I18nProperty, Locale)}
 */
@SuppressWarnings({"PublicInnerClass", "StaticMethodOnlyUsedInOneClass"})
@ExtensionMethod(TraceableUtils.class)
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code entities.shapes.impl.name} messages. */
	public enum Name {
		;

		/** I18n key with value {@code entities.shapes.impl.name.rectangle}. */
		public static final @NonNull I18nProperty RECTANGLE = new EntitiesShapesImplI18nProperty("name.rectangle");

		/** I18n key with value {@code entities.shapes.impl.name.triangle}. */
		public static final @NonNull I18nProperty TRIANGLE = new EntitiesShapesImplI18nProperty("name.triangle");

		/**
		 * I18n value retriever for key {@code entities.shapes.impl.name.rectangle}.
		 * @return the value of key {@code entities.shapes.impl.name.rectangle}
		 */
		@SuppressWarnings("unused")
		public static @NonNull String RECTANGLE() {
			return RECTANGLE.getMessage().trace(Name.class);
		}

		/**
		 * I18n value retriever for key {@code entities.shapes.impl.name.triangle}.
		 * @return the value of key {@code entities.shapes.impl.name.triangle}
		 */
		@SuppressWarnings("unused")
		public static @NonNull String TRIANGLE() {
			return TRIANGLE.getMessage().trace(Name.class);
		}
	}

	/**
	 * Utility class representing an i18n property specifically for the {@code entities.shapes.impl} namespace.
	 * This class extends the {@link I18nProperty} class, allowing for the creation of specialized property keys by
	 * automatically prefixing them with "{@code entities.shapes.impl.}".
	 */
	public static class EntitiesShapesImplI18nProperty extends I18nProperty {

		/**
		 * Constructs an instance of {@code EntitiesShapesImplI18nProperty} with a specific i18n property name.
		 * Prepends the property name with "{@code entities.shapes.impl.}" to create the full property key.
		 * @param propertyName the name of the i18n property, which will be prefixed with
		 *                     "{@code entities.shapes.impl.}"
		 */
		@SuppressWarnings("StringConcatenation")
		public EntitiesShapesImplI18nProperty(final @NonNull String propertyName) {
			super("entities.shapes.impl." + propertyName); //NON-NLS
		}
	}
}
