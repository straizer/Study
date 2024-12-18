package semester5.pwjj.utils.extensions;

import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.util.NullnessUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import semester5.pwjj.MockedStatic;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("TestMethodWithoutAssertion")
@DisplayName("Traceable Utils Tests")
final class TraceableUtilsTests extends UtilsExtensionsTestsBase {

	private static final @NonNull String STATIC_TRACED_IDENTITY = "STATIC";
	private static final @NonNull String METHOD_NAME = "method_name";
	private static final @NonNull Logger traceableUtilsTestsLoggerMock = Mockito.mock(Logger.class);

	@SuppressWarnings("UseOfConcreteClass")
	private static @MonotonicNonNull MockedStatic<ReflectionUtils> reflectionUtilsMock;

	@SuppressWarnings({"dereference.of.nullable", "argument"})
	@BeforeAll
	static void beforeAll() throws IllegalAccessException, NoSuchFieldException {
		//noinspection CallToSuspiciousStringMethod
		final @NonNull Field field = TraceableUtils.class.getDeclaredField("CACHED_LOGGERS");
		field.setAccessible(true);
		//noinspection unchecked
		((Map<@NonNull Class<?>, @NonNull Logger>) field.get(null))
			.put(TraceableUtilsTests.class, traceableUtilsTestsLoggerMock);
		reflectionUtilsMock = new MockedStatic<>(ReflectionUtils.class);
		reflectionUtilsMock.when(() -> ReflectionUtils.getCallingMethodName("trace")).thenReturn(METHOD_NAME);
	}

	@AfterAll
	static void afterAll() {
		//noinspection StaticVariableUsedBeforeInitialization
		NullnessUtil.castNonNull(reflectionUtilsMock).close();
	}

	private static void verifyReflectionUtilsMockCalled() {
		//noinspection StaticVariableUsedBeforeInitialization
		NullnessUtil.castNonNull(reflectionUtilsMock).verify(() -> ReflectionUtils.getCallingMethodName("trace"));
	}

	@BeforeEach
	void beforeEach() {
		NullnessUtil.castNonNull(reflectionUtilsMock).clearInvocations();
		Mockito.clearInvocations(traceableUtilsTestsLoggerMock);
	}

	@AfterEach
	void afterEach() {
		NullnessUtil.castNonNull(reflectionUtilsMock).verifyNoMoreInteractions();
		Mockito.verifyNoMoreInteractions(traceableUtilsTestsLoggerMock);
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
		final int value = 123;
		final int identity = 123;
		traceTest(() -> TraceableUtils.trace(value, getClass(), identity), value, value, identity);
		verifyReflectionUtilsMockCalled();
	}

	@DisplayName("trace no method name and identity hash code")
	@Test
	void traceNoMethodNameAndIdentityHashCodeTest() {
		final int value = 123;
		traceTest(() -> TraceableUtils.trace(value, getClass()), value, value, STATIC_TRACED_IDENTITY);
		verifyReflectionUtilsMockCalled();
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

	@SuppressWarnings({"return", "OverloadedMethodsWithSameNumberOfParameters"})
	private void traceTest(
		final @Nullable Object value, final @NonNull Object expectedTracedValue,
		final @Nullable Integer identity, final @NonNull Object expectedTracedIdentity
	) {
		traceTest(() -> TraceableUtils.trace(value, getClass(), METHOD_NAME, identity),
			value, expectedTracedValue, expectedTracedIdentity);
	}

	@SuppressWarnings({"argument", "OverloadedMethodsWithSameNumberOfParameters"})
	private void traceTest(
		final @NonNull Supplier<@Nullable Object> traceCall, final @Nullable Object value,
		final @NonNull Object expectedTracedValue, final @NonNull Object expectedTracedIdentity
	) {
		Assertions.assertThat(traceCall.get()).isEqualTo(value);
		traceTest(expectedTracedValue, expectedTracedIdentity, METHOD_NAME);
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
