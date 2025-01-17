package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

/** Utility class providing extensions and helper methods for working with arrays in Java. */
@SuppressWarnings("ClassWithoutLogger")
@UtilityClass
@ExtensionMethod(Arrays.class)
public class ArrayUtils {

	/**
	 * Returns a {@link Stream} containing the elements of the provided {@code array}, after skipping the first
	 * {@code n} elements.
	 * If the array contains fewer than {@code n} elements, an empty {@link Stream} is returned.
	 * @param array the {@code array} to create a {@link Stream} from
	 * @param n     the number of leading elements to skip
	 * @param <T>   the type of elements in the {@code array}
	 * @return a {@link Stream} starting from the (n+1)-th element of the array
	 * @throws IllegalArgumentException if {@code n} is negative
	 */
	public <@Nullable T> Stream<@PolyNull T> skip(
		final @PolyNull T[] array, @SuppressWarnings("StandardVariableNames") final long n) {
		return array.stream().skip(n);
	}

	/**
	 * Applies the specified {@code mapper} {@link Function} to each element of the given {@code array}, producing a new
	 * {@link Stream} consisting of the mapped elements.
	 * @param array  the array whose elements will be transformed by the {@code mapper}
	 * @param mapper the {@link Function} to apply to each element of the {@code array}
	 * @param <T>    the type of elements in the input {@code array}
	 * @param <R>    the type of elements in the resulting {@link Stream}
	 * @return a {@link Stream} consisting of the transformed elements
	 */
	public <@Nullable T, @Nullable R> Stream<@Nullable R> map(
		final @PolyNull T[] array, final Function<? super @PolyNull T, ? extends @Nullable R> mapper
	) {
		return array.stream().map(mapper);
	}

	/**
	 * Checks whether a specified element is present in the given array.
	 * @param array   the array to search for the specified element
	 * @param element the element to find in the array
	 * @param <T>     the type of the {@code element} and the elements in the array
	 * @return {@code true} if the array contains the specified element; otherwise {@code false}
	 */
	@SuppressWarnings("argument")
	public <@Nullable T> boolean contains(final @PolyNull T[] array, final @Nullable T element) {
		return array.asList().contains(element);
	}
}
