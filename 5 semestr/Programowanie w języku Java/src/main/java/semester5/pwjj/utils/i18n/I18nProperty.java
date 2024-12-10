package semester5.pwjj.utils.i18n;

import lombok.Data;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.Representative;

/** Base class for storing i18n property constants. */
@Data
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
		return Representative.traceNonNull(instance, I18nProperty.class);
	}
}
