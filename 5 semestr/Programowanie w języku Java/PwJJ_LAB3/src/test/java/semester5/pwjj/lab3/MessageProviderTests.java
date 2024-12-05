package semester5.pwjj.lab3;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import semester5.pwjj.lab3.i18n.MessageProvider;
import semester5.pwjj.lab3.i18n.Messages;

import java.util.Locale;
import java.util.stream.Stream;

@DisplayName("MessageProvider Tests")
class MessageProviderTests extends TestsBase {

	private static final Messages.I18nProperty PROPERTY_NAME = Messages.Test.MESSAGE;

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
	@ParameterizedTest
	@MethodSource
	void getCustomTest(@NonNull final Locale locale, @NonNull final String expected) {
		Assertions.assertThat(MessageProvider.get(PROPERTY_NAME, locale)).isEqualTo(expected);
	}
}
