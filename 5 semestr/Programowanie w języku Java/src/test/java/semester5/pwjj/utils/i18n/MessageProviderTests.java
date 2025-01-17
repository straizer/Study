package semester5.pwjj.utils.i18n;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;

@DisplayName("Message Provider Tests")
final class MessageProviderTests extends UtilsI18nTestsBase {

	@DisplayName("get default translation")
	@Test
	void getDefaultTest() {
		getDefaultTest(MessageProvider::get);
	}

	@DisplayName("get custom translation")
	@ParameterizedTest(name = "[{index}] {0}")
	@MethodSource
	void getCustomTest(final Locale locale, final String expected) {
		getCustomTest(MessageProvider::get, locale, expected);
	}

	@DisplayName("non-existing translation language")
	@Test
	void nonExistingTranslationLanguageTest() {
		nonExistingTranslationLanguageTest(MessageProvider::get);
	}

	@DisplayName("non-existing translation property")
	@Test
	void nonExistingTranslationPropertyTest() {
		nonExistingTranslationPropertyTest(MessageProvider::get);
	}
}
