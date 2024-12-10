package semester5.pwjj.utils;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import semester5.pwjj.TestsBase;
import semester5.pwjj.utils.i18n.MessageProvider;

import static org.mockito.Mockito.times;

@DisplayName("Nullable Utils Tests")
final class StringUtilsTests extends TestsBase {

	private static final @NonNull String TEMPLATE = "%d";

	@DisplayName("valid format")
	@Test
	void validFormatTest() {
		Assertions.assertThat(StringUtils.format(TEMPLATE, 0)).isEqualTo("0");
	}

	@DisplayName("invalid format")
	@Test
	void invalidFormatTest() {
		Assertions.assertThat(StringUtils.format(TEMPLATE, StringUtils.EMPTY)).isEqualTo("%d");
		messageProviderMock.verify(() -> MessageProvider.get(UTILS_ERROR_FORMATTING), times(1));
	}
}
