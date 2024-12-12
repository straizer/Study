package semester5.pwjj.utils.i18n;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;

@DisplayName("Message Provider Tests")
final class I18nPropertyTests extends I18nTestBase {

	@DisplayName("get default translation")
	@Test
	void getDefaultTest() {
		getDefaultTest(I18nProperty::getMessage);
	}

	@DisplayName("get custom translation")
	@ParameterizedTest(name = "[{index}] {0}")
	@MethodSource
	void getCustomTest(final @NonNull Locale locale, final @NonNull String expected) {
		getCustomTest(I18nProperty::getMessage, locale, expected);
	}

	@DisplayName("non-existing translation language")
	@Test
	void nonExistingTranslationLanguageTest() {
		nonExistingTranslationLanguageTest(I18nProperty::getMessage);
	}

	@DisplayName("non-existing translation property")
	@Test
	void nonExistingTranslationPropertyTest() {
		nonExistingTranslationPropertyTest(I18nProperty::getMessage);
	}
}
