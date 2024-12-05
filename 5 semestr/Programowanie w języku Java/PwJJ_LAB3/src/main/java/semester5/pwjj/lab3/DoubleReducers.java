package semester5.pwjj.lab3;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.DoubleBinaryOperator;

/**
 * Class containing reducing operations for {@code double}.
 */
public enum DoubleReducers {
	;

	/**
	 * Reducer reducing by multiplying.
	 */
	@NonNull
	public static final DoubleBinaryOperator MULTIPLYING = (first, second) -> first * second;
}
