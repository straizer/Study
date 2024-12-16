package semester5.pwjj.utils;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.slf4j.Logger;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.extensions.TraceableUtils;

/**
 * Represents an abstract interface for objects that support trace logging functionality.
 * Classes implementing this interface can log specific information about their state
 * or behavior using the provided trace methods.
 * These methods aid in debugging and provide insights into the object's lifecycle or method execution.
 */
@ExtensionMethod(TraceableUtils.class)
public interface Traceable {

	/**
	 * Logs at the TRACE level message using the {@link Logger} named after this object's class.
	 * The logged message format is {@code {class}@{address}::{method} -> {value}}, where:
	 * <ul>
	 *     <li>{@code class}: the simple name of this object's class.</li>
	 *     <li>{@code address}: the {@code identityHashCode} of this object.</li>
	 *     <li>{@code method}: the first method name on the stack trace, which isn't '{@code trace}'.</li>
	 *     <li>{@code value}: the formatted {@code returnValue}.</li>
	 * </ul>
	 * The method then returns the {@code returnValue}.
	 * <p>
	 * If the method name can't be retrieved due to security restrictions,
	 * a {@link StringUtils#UNKNOWN} is logged instead.
	 * @param returnValue the value to log and return
	 * @param <T>         the type of the {@code returnValue}
	 * @return the {@code returnValue}
	 */
	default <@Nullable T> @PolyNull T trace(final @PolyNull T returnValue) {
		return returnValue.trace(getClass(), System.identityHashCode(this));
	}
}
