package semester5.pwjj.entities;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
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
@ExtensionMethod({StringUtils.class, TraceableUtils.class})
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code entities.toString} messages. */
	public enum ToString {
		;

		/** I18n key with value {@code entities.toString.color}. */
		public static final @NonNull I18nProperty COLOR = new EntitiesI18nProperty("toString.color");

		/**
		 * I18n value retriever for key {@code entities.toString.color}.
		 * @param red   the value of red in range [0-255]
		 * @param green the value of green in range [0-255]
		 * @param blue  the value of blue in range [0-255]
		 * @param alpha the value of transparency in range [0-255]
		 * @return the formatted value of key {@code entities.toString.color}
		 */
		public static @NonNull String COLOR(final int red, final int green, final int blue, final int alpha) {
			return COLOR.getMessage().safeFormat(red, green, blue, alpha).trace(ToString.class);
		}
	}

	/**
	 * Utility class representing an i18n property specifically for the {@code entities} namespace.
	 * This class extends the {@link I18nProperty} class, allowing for the creation of specialized property keys by
	 * automatically prefixing them with "{@code entities.}".
	 */
	@SuppressWarnings("ClassNamePrefixedWithPackageName")
	public static class EntitiesI18nProperty extends I18nProperty {

		/**
		 * Constructs an instance of {@code EntitiesI18nProperty} with a specific i18n property name.
		 * Prepends the property name with "{@code entities.}" to create the full property key.
		 * @param propertyName the name of the i18n property, which will be prefixed with "{@code entities.}"
		 */
		@SuppressWarnings("StringConcatenation")
		public EntitiesI18nProperty(final @NonNull String propertyName) {
			super("entities." + propertyName); //NON-NLS
		}
	}
}
