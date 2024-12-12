package semester5.pwjj.utils.i18n;

import lombok.Data;
import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.utils.RepresentativeUtils;

import java.util.Locale;

/** Base class for storing i18n property constants. */
@Data
@ExtensionMethod(RepresentativeUtils.class)
public abstract class I18nProperty {

	private final @NonNull String propertyName;

	/**
	 * Creates an anonymous object of type {@code I18nProperty}.
	 * @param propertyName name of I18n property
	 * @return new anonymous object
	 */
	public static @NonNull I18nProperty of(final @NonNull String propertyName) {
		final @NonNull I18nProperty instance = new I18nProperty(propertyName) {
		};
		return instance.traceNonNull(I18nProperty.class);
	}

	/**
	 * Gets i18nized message.
	 * @return message in current {@link Locale}
	 */
	public @NonNull String getMessage() {
		return MessageProvider.get(this);
	}

	/**
	 * Gets i18nized message.
	 * @param locale locale to retrieve the message in
	 * @return message in current {@link Locale}
	 */
	public @NonNull String getMessage(final @NonNull Locale locale) {
		return MessageProvider.get(this, locale);
	}
}
