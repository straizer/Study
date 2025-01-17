package semester5.pwjj.utils.extensions;

import lombok.Cleanup;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import semester5.pwjj.MockedStatic;

@DisplayName("Reflection Utils Tests")
final class ReflectionUtilsTests extends UtilsExtensionsTestsBase {

	@DisplayName("get calling method name")
	@Test
	void getCallingMethodNameTest() {
		Assertions.assertThat(ReflectionUtils.getCallingMethodName()).isEqualTo("getCallingMethodNameTest");
	}

	@DisplayName("get calling method name with skip")
	@Test
	void getCallingMethodNameWithSkipTest() {
		Assertions.assertThat(ReflectionUtils.getCallingMethodName("getCallingMethodNameWithSkipTest"))
			.isEqualTo("invoke");
	}

	@DisplayName("get calling method name throwing")
	@Test
	void getCallingMethodNameThrowingTest() {
		//noinspection UseOfConcreteClass
		@Cleanup final @NonNull MockedStatic<StreamUtils> streamUtilsMock = new MockedStatic<>(StreamUtils.class);
		streamUtilsMock.when(() -> StreamUtils.getFirst(ArgumentMatchers.any())).thenThrow(SecurityException.class);
		Assertions.assertThat(ReflectionUtils.getCallingMethodName()).isEqualTo(StringUtils.UNKNOWN);
		streamUtilsMock.verify(() -> StreamUtils.getFirst(ArgumentMatchers.any()));
	}
}
