package semester5.pwjj.entities.shapes.impl;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.params.provider.Arguments;
import semester5.pwjj.ErrorTestsBase;
import semester5.pwjj.entities.shapes.Shape;

import java.util.stream.Stream;

/** Abstract base class for {@code entities.shapes.impl} error test setups and utilities. */
public abstract class EntitiesShapesImplErrorTestsBase extends EntitiesShapesImplTestsBase implements ErrorTestsBase {

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
