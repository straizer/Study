package semester5.pwjj.utils;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.extensions.TraceableUtils;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code utils} in the current {@link Locale}.
 * @implNote If different translation is required without changing default {@link Locale},
 * use {@link MessageProvider#get(I18nProperty, Locale)}
 */
@SuppressWarnings({"PublicInnerClass", "StaticMethodOnlyUsedInOneClass"})
@ExtensionMethod({StringUtils.class, TraceableUtils.class})
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code utils.error} messages. */
	public enum Error {
		;

		/** I18n key with value {@code utils.error.noFileFound}. */
		public static final @NonNull I18nProperty NO_FILE_FOUND = new UtilsI18nProperty("error.noFileFound");

		/** I18n key with value {@code utils.error.noReadAccess}. */
		public static final @NonNull I18nProperty NO_READ_ACCESS = new UtilsI18nProperty("error.noReadAccess");

		/** I18n key with value {@code utils.error.readingFailed}. */
		public static final @NonNull I18nProperty READING_FAILED = new UtilsI18nProperty("error.readingFailed");

		/**
		 * I18n value retriever for key {@code utils.error.noFileFound}.
		 * @param filename name of the file
		 * @return the formatted value of key {@code utils.error.noFileFound}
		 */
		public static @NonNull String NO_FILE_FOUND(final @NonNull String filename) {
			return NO_FILE_FOUND.getMessage().safeFormat(filename).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.noReadAccess}.
		 * @param filename name of the file
		 * @return the formatted value of key {@code utils.error.noReadAccess}
		 */
		public static @NonNull String NO_READ_ACCESS(final @NonNull String filename) {
			return NO_READ_ACCESS.getMessage().safeFormat(filename).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.readingFailed}.
		 * @param filename  name of the file
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.error.readingFailed}
		 */
		public static @NonNull String READING_FAILED(
			final @NonNull String filename, final @NonNull Exception exception
		) {
			return READING_FAILED.getMessage().safeFormat(filename, exception.getMessage()).trace(Error.class);
		}
	}

	/**
	 * Utility class representing an i18n property specifically for the {@code utils} namespace.
	 * This class extends the {@link I18nProperty} class, allowing for the creation of specialized property keys by
	 * automatically prefixing them with "{@code utils.}".
	 */
	@SuppressWarnings("ClassNamePrefixedWithPackageName")
	public static class UtilsI18nProperty extends I18nProperty {

		/**
		 * Constructs an instance of {@code UtilsExtensionsI18nProperty} with a specific i18n property name.
		 * Prepends the property name with "{@code utils.}" to create the full property key.
		 * @param propertyName the name of the i18n property, which will be prefixed with "{@code utils.}"
		 */
		@SuppressWarnings("StringConcatenation")
		public UtilsI18nProperty(final @NonNull String propertyName) {
			super("utils." + propertyName); //NON-NLS
		}
	}
}
