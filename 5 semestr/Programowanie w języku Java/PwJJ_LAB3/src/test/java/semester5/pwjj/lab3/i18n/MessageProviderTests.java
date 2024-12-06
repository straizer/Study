package semester5.pwjj.lab3.i18n;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.lab3.TestsBase;

import java.util.Locale;
import java.util.stream.Stream;

@DisplayName("Message Provider Tests")
class MessageProviderTests extends TestsBase {

	private static final MessageProvider.I18nProperty PROPERTY_NAME = Messages.Test.MESSAGE;

	@NonNull
	private static Stream<Arguments> getCustomTest() {
		return Stream.of(
			Arguments.of(Locale.of("pl"), "testowa wiadomość"),
			Arguments.of(Locale.ENGLISH, "test message"));
	}

	@DisplayName("get default translation by messages")
	@Test
	void getDefaultByMessagesTest() {
		Locale.setDefault(Locale.ENGLISH);
		Assertions.assertThat(Messages.Test.MESSAGE()).isEqualTo("test message");
	}

	@DisplayName("get default translation")
	@Test
	void getDefaultTest() {
		Locale.setDefault(Locale.ENGLISH);
		Assertions.assertThat(MessageProvider.get(PROPERTY_NAME)).isEqualTo("test message");
	}

	@DisplayName("get custom translation")
	@ParameterizedTest(name = "[{index}] {0}")
	@MethodSource
	void getCustomTest(@NonNull final Locale locale, @NonNull final String expected) {
		Assertions.assertThat(MessageProvider.get(PROPERTY_NAME, locale)).isEqualTo(expected);
	}

	@DisplayName("non-existing translation language")
	@Test
	void nonExistingTranslationLanguageTest() {
		Locale.setDefault(Locale.FRENCH);
		Assertions.assertThat(Messages.Test.MESSAGE())
			.isEqualTo("<%s:%s>", Locale.getDefault(), Messages.Test.MESSAGE.propertyName());
	}

	@DisplayName("non-existing translation property")
	@Test
	void nonExistingTranslationPropertyTest() {
		final String propertyName = "test.missing";
		Assertions.assertThat(MessageProvider.get(new MessageProvider.I18nProperty(propertyName)))
			.isEqualTo("<%s:%s>", Locale.ROOT, propertyName);
	}
}
