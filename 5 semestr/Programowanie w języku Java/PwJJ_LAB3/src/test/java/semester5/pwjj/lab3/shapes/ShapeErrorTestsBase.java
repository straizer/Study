package semester5.pwjj.lab3.shapes;

import org.assertj.core.api.ThrowableAssert;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.lab3.ErrorTestsBase;
import semester5.pwjj.lab3.i18n.MessageProvider;
import semester5.pwjj.lab3.i18n.Messages;

import java.util.Locale;

abstract class ShapeErrorTestsBase extends ErrorTestsBase {

	static void getPerimeterNonPositiveSideTest(
		@NonNull final Locale locale,
		final ThrowableAssert.ThrowingCallable callable,
		@NonNull final String shapeName) {
		throwsIllegalArgumentException(locale, callable,
			() -> Messages.Error.SIDES_NOT_POSITIVE(
				MessageProvider.get(new MessageProvider.I18nProperty("name." + shapeName))));
	}
}
