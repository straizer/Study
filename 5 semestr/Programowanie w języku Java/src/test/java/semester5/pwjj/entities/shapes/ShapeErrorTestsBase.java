package semester5.pwjj.entities.shapes;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.params.provider.Arguments;
import semester5.pwjj.ErrorTestsBase;

import java.util.stream.Stream;

/** Base class for all {@link Shape} error tests. */
public abstract class ShapeErrorTestsBase extends ErrorTestsBase {

	protected static @MonotonicNonNull Shape emptyShape;

	/** Method supplying {@link org.junit.jupiter.params.ParameterizedTest} in each extending class. */
	@SuppressWarnings({"unused", "methodref.receiver.bound"})
	private static @NonNull Stream<Arguments> noSidesTest() {
		//noinspection StaticVariableUsedBeforeInitialization
		return Stream.of(
			Arguments.argumentSet("call getPerimeter()", (ThrowingCallable) emptyShape::getPerimeter),
			Arguments.argumentSet("call getArea()", (ThrowingCallable) emptyShape::getArea));
	}
}
