package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("TestMethodWithoutAssertion")
@DisplayName("Stream Utils Tests")
@ExtensionMethod(StreamUtils.class)
final class StreamUtilsTests extends UtilsExtensionsTestsBase {

	private static final @NonNull String DELIMITER = ",";

	private static final @NonNull Supplier<Stream<String>> STRING_STREAM_SUPPLIER = () -> Stream.of("a", "b", "c");

	@DisplayName("joining")
	@Test
	void joiningTest() {
		Assertions.assertThat(STRING_STREAM_SUPPLIER.get().joining(DELIMITER, "<", ">")).isEqualTo("<a,b,c>");
	}

	@DisplayName("joining default prefix suffix")
	@Test
	void joiningDefaultPrefixSuffixTest() {
		Assertions.assertThat(STRING_STREAM_SUPPLIER.get().joining(DELIMITER)).isEqualTo("[a,b,c]");
	}

	@DisplayName("get first")
	@Test
	void getFirstTest() {
		Assertions.assertThat(STRING_STREAM_SUPPLIER.get().getFirst()).isEqualTo("a");
	}
}
