package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import org.checkerframework.checker.initialization.qual.UnknownInitialization;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class providing extended logging capabilities and value formatting for classes.
 * The main functionality involves logging at the TRACE level with dynamically formatted messages
 * and thread-safe caching of {@link Logger} instances for a reuse.
 * Methods in this class are designed to format and log data, enabling easier debugging and
 * traceability of method return values and execution paths.
 */
@SuppressWarnings("ClassWithoutLogger")
@UtilityClass
@ExtensionMethod({Objects.class, ArrayUtils.class, StreamUtils.class, System.class})
public class TraceableUtils {

	/**
	 * A thread-safe cache for storing and retrieving {@link Logger} instances.
	 * The {@code CACHED_LOGGERS} {@link Map} associates a {@link Class} with its corresponding {@link Logger},
	 * ensuring that each {@link Class} only has one {@link Logger} instance in the cache.
	 * The cache is implemented using a {@link ConcurrentHashMap} to support
	 * concurrent access in multithreaded environments.
	 */
	@SuppressWarnings("StaticCollection")
	private final @NonNull Map<@NonNull Class<?>, @NonNull Logger> CACHED_LOGGERS = new ConcurrentHashMap<>(10);

	/**
	 * Logs at the TRACE level message using the {@link Logger} named after this {@code instance}'s class.
	 * The logged message format is<br>
	 * {@code {class}@{address}::<ctor> -> {value}}, where:
	 * <ul>
	 *     <li>{@code class}: the simple name of this {@code instance}'s class.</li>
	 *     <li>{@code address}: the {@code identityHashCode} of {@code instance}.</li>
	 *     <li>{@code value}: {@link String} representation of {@code instance}.</li>
	 * </ul>
	 * <p>
	 * This method is designed for use in constructors to log information about newly created objects,
	 * aiding in debugging and tracing object creation.
	 * @param instance the instance whose constructor is logged
	 * @param <T>      the type of the {@code instance}
	 */
	@SuppressWarnings("argument")
	public <@NonNull T> void traceConstructor(final @UnknownInitialization @NonNull T instance) {
		trace(instance, instance.getClass(), "<ctor>", instance.identityHashCode()); //NON-NLS
	}

	/**
	 * Logs at the TRACE level message using a {@link Logger} named after the specified {@code callingClass}.
	 * The logged message format is<br>
	 * {@code {class}@STATIC::{method} -> {value}}, where:
	 * <ul>
	 *     <li>{@code class}: the simple name of {@code callingClass}.</li>
	 *     <li>{@code method}: the first method name on the stack trace, which isn't '{@code trace}'.</li>
	 *     <li>{@code value}: the formatted {@code returnValue}.</li>
	 * </ul>
	 * The method then returns the {@code returnValue}.
	 * <p>
	 * If the method name can't be retrieved due to security restrictions,
	 * a {@link StringUtils#UNKNOWN} is logged instead.
	 * @param returnValue  the value to log and return
	 * @param callingClass the {@link Class} for which the {@link Logger} is retrieved
	 * @param <T>          the type of the {@code returnValue}
	 * @return the {@code returnValue}
	 */
	public <@Nullable T> @PolyNull T trace(final @PolyNull T returnValue, final @NonNull Class<?> callingClass) {
		return trace(returnValue, callingClass, null);
	}

	/**
	 * Logs at the TRACE level message using a {@link Logger} named after the specified {@code callingClass}.
	 * The logged message format is<br>
	 * {@code {class}@{address}::{method} -> {value}}, where:
	 * <ul>
	 *     <li>{@code class}: the simple name of {@code callingClass}.</li>
	 *     <li>{@code address}: the {@code identityHashCode} if provided; otherwise `{@code STATIC}`.</li>
	 *     <li>{@code method}: the first method name on the stack trace, which isn't '{@code trace}'.</li>
	 *     <li>{@code value}: the formatted {@code returnValue}.</li>
	 * </ul>
	 * The method then returns the {@code returnValue}.
	 * <p>
	 * If the method name can't be retrieved due to security restrictions,
	 * a {@link StringUtils#UNKNOWN} is logged instead.
	 * @param returnValue      the value to log and return
	 * @param callingClass     the {@link Class} for which the {@link Logger} is retrieved
	 * @param identityHashCode the identity hash code of the object to associate with the log entry
	 * @param <T>              the type of the {@code returnValue}
	 * @return the {@code returnValue}
	 */
	@SuppressWarnings("return")
	public <@Nullable T> @PolyNull T trace(
		final @PolyNull T returnValue, final @NonNull Class<?> callingClass, final @Nullable Integer identityHashCode
	) {
		@NonNull String methodName = StringUtils.UNKNOWN;
		try {
			methodName = Thread.currentThread().getStackTrace().skip(2)
				.dropWhile(it -> it.getMethodName().equals("trace")).getFirst().getMethodName();
		} catch (final SecurityException _) {
			getLogger(TraceableUtils.class)
				.trace("Unable to retrieve method name; falling back to {}", methodName); //NON-NLS
		}
		return trace(returnValue, callingClass, methodName, identityHashCode);
	}

	/**
	 * Logs at the TRACE level message using a {@link Logger} named after the specified {@code callingClass}.
	 * The logged message format is<br>
	 * {@code {class}@{address}::{method} -> {value}}, where:
	 * <ul>
	 *     <li>{@code class}: the simple name of {@code callingClass}.</li>
	 *     <li>{@code address}: the {@code identityHashCode} if provided; otherwise `{@code STATIC}`.</li>
	 *     <li>{@code method}: the specified {@code methodName}.</li>
	 *     <li>{@code value}: the formatted {@code returnValue}.</li>
	 * </ul>
	 * The method then returns the {@code returnValue}.
	 * @param returnValue      the value to log and return
	 * @param callingClass     the {@link Class} for which the {@link Logger} is retrieved
	 * @param methodName       the name of the method generating the log entry
	 * @param identityHashCode the identity hash code of the object to associate with the log entry
	 * @param <T>              the type of the {@code returnValue}
	 * @return the {@code returnValue}
	 */
	@SuppressWarnings("argument")
	public <@Nullable T> @UnknownInitialization @PolyNull T trace(
		final @UnknownInitialization @PolyNull T returnValue, final @NonNull Class<?> callingClass,
		final @NonNull String methodName, final @Nullable Integer identityHashCode
	) {
		getLogger(callingClass)
			.trace("{}@{}::{} -> {}",
				callingClass.getSimpleName(), identityHashCode.requireNonNullElse("STATIC"), methodName,
				getFormattedValue(returnValue));
		return returnValue;
	}

	/**
	 * Retrieves a {@link Logger} instance associated with the specified {@code clazz}.
	 * If a logger for the given {@code clazz} is already cached, it'll be returned;
	 * otherwise, a new {@link Logger} will be created and cached.
	 * @param clazz the {@link Class} for which the {@link Logger} is requested
	 * @return a {@link Logger} instance associated with the specified {@code clazz}
	 */
	private @NonNull Logger getLogger(final @NonNull Class<?> clazz) {
		return CACHED_LOGGERS.computeIfAbsent(clazz, LoggerFactory::getLogger);
	}

	/**
	 * Returns a formatted value for the given {@code value}. The formatting is applied based on the type {@link T}.
	 * <p>
	 * If {@link T} is a {@link String}, line separators are replaced with `{@code \n}`.
	 * <p>
	 * If {@link T} is an {@link Optional}, its value is returned if present; otherwise, a {@link StringUtils#NULL} is
	 * returned.
	 * <p>
	 * {@code null} values are replaced with a {@link StringUtils#NULL}.
	 * <p>
	 * For all other types, the original value is returned as-is.
	 * @param value the value to be formatted; can be null
	 * @param <T>   the type of {@code value}
	 * @return a formatted value
	 */
	private <@Nullable T> @UnknownInitialization @NonNull Object getFormattedValue(
		final @UnknownInitialization @Nullable T value
	) {
		return switch (value) {
			case final @NonNull String s -> //noinspection HardcodedLineSeparator,HardcodedFileSeparator
				s.replace("\n", "\\n"); //NON-NLS
			case final @NonNull Optional<?> o -> o.isPresent() ? o.get() : StringUtils.NULL;
			case null -> StringUtils.NULL;
			default -> value;
		};
	}
}
