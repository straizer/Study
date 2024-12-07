package semester5.pwjj.entities;

import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.utils.ReturnLogger;
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
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code entities.toString} messages. */
	public enum ToString {
		;

		/** I18n key with value {@code entities.toString.color}. */
		public static final @NonNull I18nProperty COLOR = new EntitiesI18nProperty("toString.color");

		/**
		 * I18n value retriever for key {@code entities.toString.color}.
		 * @param red   value of red in range [0-255]
		 * @param green value of green in range [0-255]
		 * @param blue  value of blue in range [0-255]
		 * @param alpha value of transparency in range [0-255]
		 * @return value of key {@code entities.toString.color}
		 */
		public static @NonNull String COLOR(final int red, final int green, final int blue, final int alpha) {
			return ReturnLogger.traceNotNull(
				StringUtils.format(MessageProvider.get(COLOR), red, green, blue, alpha), ToString.class);
		}
	}


	/** Class storing i18n property constants for package {@code entities}. */
	public static class EntitiesI18nProperty extends I18nProperty {

		/**
		 * Creates an object of type {@code I18nProperty}.
		 * @param propertyName name of I18n property
		 */
		public EntitiesI18nProperty(@NonNull final String propertyName) {
			super("entities." + propertyName); //NON-NLS
		}
	}
}
