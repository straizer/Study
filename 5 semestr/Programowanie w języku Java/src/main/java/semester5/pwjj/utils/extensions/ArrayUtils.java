package semester5.pwjj.utils.extensions;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.stream.Stream;

/** Class containing native Java array utils. */
@UtilityClass
@ExtensionMethod(Arrays.class)
public class ArrayUtils {

	/**
	 * Creates {@link Stream} from native Java array and performs {@link Stream#skip(long)} operation on it.
	 * @see Stream#skip(long)
	 */
	public <T> Stream<T> skip(final @NonNull T @NonNull [] array, final long n) {
		return array.stream().skip(n);
	}
}
