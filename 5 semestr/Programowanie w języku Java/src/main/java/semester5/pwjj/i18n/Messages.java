package semester5.pwjj.i18n;

import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.Color;
import semester5.pwjj.ReturnLogger;
import semester5.pwjj.i18n.MessageProvider.I18nProperty;

import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages in the current locale.
 *
 * @implNote If different translation is required without changing default locale,
 * use {@link MessageProvider#get(I18nProperty propertyName, Locale locale)}
 */
public enum Messages {
	;

	public enum Error {
		;

		public static final I18nProperty SIDES_NOT_POSITIVE = new I18nProperty("error.sidesNotPositive");
		public static final I18nProperty TRIANGLE_RULE = new I18nProperty("error.triangleRule");

		public static String SIDES_NOT_POSITIVE(@NonNull final String arg1) {
			return ReturnLogger._log(String.format(MessageProvider.get(SIDES_NOT_POSITIVE), arg1), Error.class);
		}

		public static String TRIANGLE_RULE() {
			return ReturnLogger._log(MessageProvider.get(TRIANGLE_RULE), Error.class);
		}
	}

	public enum Name {
		;

		public static final I18nProperty RECTANGLE = new I18nProperty("name.rectangle");
		public static final I18nProperty TRIANGLE = new I18nProperty("name.triangle");

		public static String RECTANGLE() {
			return ReturnLogger._log(MessageProvider.get(RECTANGLE), Name.class);
		}

		public static String TRIANGLE() {
			return ReturnLogger._log(MessageProvider.get(TRIANGLE), Name.class);
		}
	}

	public enum ToString {
		;

		public static final I18nProperty COLOR = new I18nProperty("toString.color");
		public static final I18nProperty SHAPE = new I18nProperty("toString.shape");

		public static String COLOR(final int arg1, final int arg2, final int arg3, final int arg4) {
			return ReturnLogger._log(String.format(MessageProvider.get(COLOR), arg1, arg2, arg3, arg4), ToString.class);
		}

		public static String SHAPE(@NonNull final String arg1, final double arg2, final double arg3,
		                           @NonNull final Color arg4, @NonNull final String arg5) {
			return ReturnLogger._log(
				String.format(MessageProvider.get(SHAPE), arg1, arg2, arg3, arg4, arg5), ToString.class);
		}
	}
}
