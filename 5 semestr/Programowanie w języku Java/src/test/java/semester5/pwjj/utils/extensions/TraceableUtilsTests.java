package semester5.pwjj.utils.extensions;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.Logger;
import semester5.pwjj.TestsBase;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings({"TestMethodWithoutAssertion", "ClassWithMultipleLoggers"})
@DisplayName("Traceable Utils Tests")
final class TraceableUtilsTests extends TestsBase {

	private static final @NonNull String STATIC_TRACED_IDENTITY = "STATIC";
	private static final @NonNull Logger traceableUtilsLoggerMock = Mockito.mock(Logger.class);
	private static final @NonNull Logger traceableUtilsTestsLoggerMock = Mockito.mock(Logger.class);

	@SuppressWarnings({"dereference.of.nullable", "argument"})
	@BeforeAll
	static void beforeAll() throws IllegalAccessException, NoSuchFieldException {
		//noinspection CallToSuspiciousStringMethod
		final @NonNull Field field = TraceableUtils.class.getDeclaredField("CACHED_LOGGERS");
		field.setAccessible(true);
		//noinspection unchecked
		final Map<@NonNull Class<?>, @NonNull Logger> cachedLoggers =
			(Map<@NonNull Class<?>, @NonNull Logger>) field.get(null);
		cachedLoggers.put(TraceableUtils.class, traceableUtilsLoggerMock);
		cachedLoggers.put(TraceableUtilsTests.class, traceableUtilsTestsLoggerMock);
	}

	@BeforeEach
	void beforeEach() {
		Mockito.clearInvocations(traceableUtilsLoggerMock, traceableUtilsTestsLoggerMock);
	}

	@AfterEach
	void afterEach() {
		Mockito.verifyNoMoreInteractions(traceableUtilsLoggerMock, traceableUtilsTestsLoggerMock);
	}

	@DisplayName("trace int")
	@Test
	void traceIntTest() {
		final int value = 123;
		traceTest(value, value);
	}

	@DisplayName("trace string")
	@Test
	void traceStringTest() {
		final @NonNull String value = "123";
		traceTest(value, value);
	}

	@DisplayName("trace string with new line")
	@Test
	void traceStringWithNewLineTest() {
		//noinspection HardcodedLineSeparator,HardcodedFileSeparator
		traceTest("123\n456", "123\\n456");
	}

	@DisplayName("trace optional")
	@Test
	void traceOptionalTest() {
		final int expectedTracedValue = 123;
		traceTest(Optional.of(expectedTracedValue), expectedTracedValue);
	}

	@DisplayName("trace empty optional")
	@Test
	void traceEmptyOptionalTest() {
		traceTest(Optional.empty(), StringUtils.NULL);
	}

	@DisplayName("trace null")
	@Test
	void traceNullTest() {
		traceTest(null, StringUtils.NULL);
	}

	@DisplayName("trace null identity hash code")
	@Test
	void traceNullIdentityHashCodeTest() {
		final int value = 123;
		traceTest(value, value, null, STATIC_TRACED_IDENTITY);
	}

	@DisplayName("trace no method name")
	@Test
	void traceNoMethodNameTest() {
		final @NonNull String methodName = "lambda$traceNoMethodNameTest";
		final int value = 123;
		final int identity = 123;
		traceTest(() -> TraceableUtils.trace(value, getClass(), identity), value, value, identity, methodName);
	}

	@DisplayName("trace no method name security exception")
	@Test
	void traceNoMethodNameSecurityExceptionTest() {
		final @NonNull String methodName = StringUtils.UNKNOWN;
		final int value = 123;
		final int identity = 123;
		try (final @NonNull MockedStatic<StreamUtils> streamUtilsMock = Mockito.mockStatic(StreamUtils.class)) {
			streamUtilsMock.when(() -> StreamUtils.getFirst(ArgumentMatchers.any())).thenThrow(SecurityException.class);
			traceTest(() -> TraceableUtils.trace(value, getClass(), identity), value, value, identity, methodName);
		}
		Mockito.verify(traceableUtilsLoggerMock)
			.trace("Unable to retrieve method name; falling back to {}", methodName);
	}

	@DisplayName("trace no method name and identity hash code")
	@Test
	void traceNoMethodNameAndIdentityHashCodeTest() {
		final @NonNull String methodName = "lambda$traceNoMethodNameAndIdentityHashCodeTest";
		final int value = 123;
		traceTest(() -> TraceableUtils.trace(value, getClass()), value, value, STATIC_TRACED_IDENTITY, methodName);
	}

	@DisplayName("trace constructor")
	@Test
	void traceConstructorTest() {
		TraceableUtils.traceConstructor(this);
		traceTest(this, System.identityHashCode(this), "<ctor>");
	}

	private void traceTest(final @Nullable Object value, final @NonNull Object expectedTracedValue) {
		final int identity = 123;
		traceTest(value, expectedTracedValue, identity, identity);
	}

	@SuppressWarnings("return")
	private void traceTest(
		final @Nullable Object value, final @NonNull Object expectedTracedValue,
		final @Nullable Integer identity, final @NonNull Object expectedTracedIdentity
	) {
		final @NonNull String methodName = "method_name";
		traceTest(() -> TraceableUtils.trace(value, getClass(), methodName, identity),
			value, expectedTracedValue, expectedTracedIdentity, methodName);
	}

	@SuppressWarnings("argument")
	private void traceTest(
		final @NonNull Supplier<@Nullable Object> traceCall, final @Nullable Object value,
		final @NonNull Object expectedTracedValue, final @NonNull Object expectedTracedIdentity,
		final @NonNull String methodName
	) {
		Assertions.assertThat(traceCall.get()).isEqualTo(value);
		traceTest(expectedTracedValue, expectedTracedIdentity, methodName);
	}

	private void traceTest(
		final @NonNull Object expectedTracedValue, final @NonNull Object expectedTracedIdentity,
		final @NonNull String methodName
	) {
		Mockito.verify(traceableUtilsTestsLoggerMock).trace(
			ArgumentMatchers.eq("{}@{}::{} -> {}"), ArgumentMatchers.eq(getClass().getSimpleName()),
			ArgumentMatchers.eq(expectedTracedIdentity), ArgumentMatchers.startsWith(methodName),
			ArgumentMatchers.eq(expectedTracedValue));
	}
}
