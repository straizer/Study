package semester5.pwjj.utils;

import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.DoubleBinaryOperator;

/**
 * Utility class providing predefined {@link DoubleBinaryOperator} implementations for
 * performing mathematical operations on {@code double} values.
 * This class offers reusable and standardized lambda-based operators.
 * The goal of the class is to reduce redundancy and improve code readability where those operations are needed.
 */
@SuppressWarnings("ClassWithoutLogger")
@UtilityClass
public class DoubleReducers {

	/**
	 * A {@link DoubleBinaryOperator} that performs multiplication of two {@code double} values.
	 * This operator takes two input arguments and produces a single {@code double} result by multiplying the inputs.
	 * @see DoubleBinaryOperator
	 */
	public final @NonNull DoubleBinaryOperator MULTIPLYING = (x, y) -> x * y;
}
