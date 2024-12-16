package semester5.pwjj.utils.i18n;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.extensions.TraceableUtils;

import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code utils.i18n} in the current {@link Locale}.
 * @implNote If different translation is required without changing default {@link Locale},
 * use {@link MessageProvider#get(I18nProperty, Locale)}
 */
@SuppressWarnings({"PublicInnerClass", "StaticMethodOnlyUsedInOneClass",
	"ClassWithTooManyTransitiveDependents", "CyclicClassDependency"})
@ExtensionMethod({StringUtils.class, TraceableUtils.class})
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code utils.i18n.error} messages. */
	public enum Error {
		;

		/** I18n key with value {@code utils.i18n.error.translationNotFound}. */
		public static final @NonNull I18nProperty TRANSLATION_NOT_FOUND =
			new UtilsI18nI18nProperty("error.translationNotFound");

		/**
		 * I18n value retriever for key {@code utils.i18n.error.translationNotFound}.
		 * @param locale      the locale for which translation can't be found
		 * @param messageName the message name for which translation can't be found
		 * @return the formatted value of key {@code utils.i18n.error.translationNotFound}
		 */
		public static @NonNull String TRANSLATION_NOT_FOUND(
			final @NonNull Locale locale, final @NonNull String messageName
		) {
			return TRANSLATION_NOT_FOUND.getMessage().safeFormat(locale, messageName).trace(Error.class);
		}
	}

	/**
	 * Utility class representing an i18n property specifically for the {@code utils.i18n} namespace.
	 * This class extends the {@link I18nProperty} class, allowing for the creation of specialized property keys by
	 * automatically prefixing them with "{@code utils.i18n.}".
	 */
	public static class UtilsI18nI18nProperty extends I18nProperty {

		/**
		 * Constructs an instance of {@code UtilsI18nI18nProperty} with a specific i18n property name.
		 * Prepends the property name with "{@code utils.i18n.}" to create the full property key.
		 * @param propertyName the name of the i18n property, which will be prefixed with "{@code utils.i18n.}"
		 */
		@SuppressWarnings("StringConcatenation")
		public UtilsI18nI18nProperty(final @NonNull String propertyName) {
			super("utils.i18n." + propertyName); //NON-NLS
		}
	}
}
