package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Array Utils Tests")
@ExtensionMethod(ArrayUtils.class)
final class ArrayUtilsTests extends UtilsExtensionsTestsBase {

	@SuppressWarnings("MismatchedReadAndWriteOfArray")
	private static final @NonNull Integer @NonNull [] ARRAY = {0, 1, 2, 3};

	@DisplayName("skip less than length")
	@Test
	void skipLessThanLengthTest() {
		Assertions.assertThat(ARRAY.skip(2)).containsExactly(2, 3);
	}

	@DisplayName("skip more than length")
	@Test
	void skipMoreThanLengthTest() {
		Assertions.assertThat(ARRAY.skip(5)).isEmpty();
	}

	@DisplayName("map to non-null")
	@Test
	void mapToNonNullTest() {
		Assertions.assertThat(ARRAY.map(Object::toString)).containsExactly("0", "1", "2", "3");
	}

	@DisplayName("map to nullable")
	@Test
	void mapToNullableTest() {
		Assertions.assertThat(ARRAY.map(it -> it % 2 == 0 ? it : null)).containsExactly(0, null, 2, null);
	}

	@DisplayName("contains true")
	@Test
	void containsTrueTest() {
		Assertions.assertThat(ARRAY.contains(0)).isTrue();
	}

	@DisplayName("contains false")
	@Test
	void containsFalseTest() {
		Assertions.assertThat(ARRAY.contains(-1)).isFalse();
	}

	@DisplayName("contains null")
	@Test
	void containsNullTest() {
		//noinspection DataFlowIssue
		Assertions.assertThat(ARRAY.contains(null)).isFalse();
	}
}
