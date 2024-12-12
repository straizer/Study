package semester5.pwjj.entities.shapes;

import org.assertj.core.api.ThrowableAssert;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.params.provider.Arguments;
import semester5.pwjj.ErrorTestsBase;

import java.util.stream.Stream;

/** Base class for all {@link Shape} error tests. */
public abstract class ShapeErrorTestsBase extends ErrorTestsBase {

	protected static @Nullable Shape emptyShape;

	/** Method supplying {@link org.junit.jupiter.params.ParameterizedTest} in each extending class. */
	private static @NonNull Stream<Arguments> noSidesTest() {
		return Stream.of(
			Arguments.argumentSet("call getPerimeter()", (ThrowableAssert.ThrowingCallable) emptyShape::getPerimeter),
			Arguments.argumentSet("call getArea()", (ThrowableAssert.ThrowingCallable) emptyShape::getArea));
	}
}
