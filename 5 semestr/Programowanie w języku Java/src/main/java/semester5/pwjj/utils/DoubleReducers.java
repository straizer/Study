package semester5.pwjj.utils;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.DoubleBinaryOperator;

/** Class containing reducing operations for {@code double}. */
public enum DoubleReducers {
	;

	/** Multiplying reducer. */
	public static final @NonNull DoubleBinaryOperator MULTIPLYING = (x, y) -> x * y;
}
