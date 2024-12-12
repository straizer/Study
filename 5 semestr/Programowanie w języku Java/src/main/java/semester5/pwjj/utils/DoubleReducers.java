package semester5.pwjj.utils;

import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.DoubleBinaryOperator;

/** Class containing reducing operations for {@code double}. */
@UtilityClass
public class DoubleReducers {

	/** Multiplying reducer. */
	public final @NonNull DoubleBinaryOperator MULTIPLYING = (x, y) -> x * y;
}
