package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("String Utils Tests")
@ExtensionMethod(StringUtils.class)
final class StringUtilsTests extends UtilsExtensionsTestsBase {

	private static final @NonNull String TEMPLATE = "%d";

	@DisplayName("valid safe format")
	@Test
	void validSafeFormatTest() {
		Assertions.assertThat(TEMPLATE.safeFormat(0)).isEqualTo("0");
	}

	@DisplayName("invalid safe format")
	@Test
	void invalidSafeFormatTest() {
		Assertions.assertThat(TEMPLATE.safeFormat(StringUtils.EMPTY)).isEqualTo("%d");
		verifyMessageProviderMockWasUsedFor(ERROR_FORMATTING);
	}

	@DisplayName("safe format no arguments")
	@Test
	void safeFormatNoArgumentsTest() {
		Assertions.assertThat(TEMPLATE.safeFormat()).isEqualTo("%d");
	}

	@DisplayName("safe format null arguments")
	@Test
	void safeFormatNullArgumentsTest() {
		//noinspection DataFlowIssue
		Assertions.assertThat(TEMPLATE.safeFormat((Object[]) null)).isEqualTo("%d");
	}
}
