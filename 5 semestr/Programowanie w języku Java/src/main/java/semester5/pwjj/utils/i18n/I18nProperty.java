package semester5.pwjj.utils.i18n;

import lombok.Data;
import lombok.experimental.ExtensionMethod;
import semester5.pwjj.utils.extensions.TraceableUtils;

import java.util.Locale;

/** Abstract class representing an i18n property that encapsulates a key used for retrieving translations. */
@SuppressWarnings({"AbstractClassWithoutAbstractMethods", "ClassNamePrefixedWithPackageName", "ClassWithoutLogger",
	"ClassWithTooManyDependents", "ClassWithTooManyTransitiveDependents", "CyclicClassDependency"})
@Data
@ExtensionMethod(TraceableUtils.class)
public abstract class I18nProperty {

	/** The property name representing an i18n key used for retrieving translations. */
	private final String propertyName;

	/**
	 * Creates a new instance of {@code I18nProperty} with the specified {@code propertyName}.
	 * @param propertyName the name of the i18n property
	 * @return a new instance of {@code I18nProperty} associated with the given property name
	 */
	public static I18nProperty of(final String propertyName) {
		final I18nProperty instance = new I18nProperty(propertyName) {
		};
		return instance.trace(I18nProperty.class);
	}

	/**
	 * Retrieves the translation for the default {@link Locale}.
	 * If no translation is found, it logs a warning message and provides a fallback
	 * indicating the absence of the translation.
	 * @return a translated {@link String} for the default {@link Locale}
	 */
	public String getMessage() {
		return MessageProvider.get(this);
	}

	/**
	 * Retrieves the translation for the given {@code locale}.
	 * If no translation is found, it logs a warning message and provides a fallback
	 * indicating the absence of the translation.
	 * @param locale the {@link Locale} in which the translation is required
	 * @return a translated {@link String} for the given {@code locale}
	 */
	public String getMessage(final Locale locale) {
		return MessageProvider.get(this, locale);
	}
}
