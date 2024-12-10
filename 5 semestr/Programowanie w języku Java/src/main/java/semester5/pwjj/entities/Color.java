package semester5.pwjj.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.Representative;

/**
 * Record representing any color in RGBA format.
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@ToString
public final class Color implements Representative {

	private static final byte BYTE_MIN = 0;
	private static final byte BYTE_MAX = -1;

	/** Default red {@code Color}. */
	public static final @NonNull Color RED = new Color(BYTE_MAX, BYTE_MIN, BYTE_MIN);

	/** Default green {@code Color}. */
	public static final @NonNull Color GREEN = new Color(BYTE_MIN, BYTE_MAX, BYTE_MIN);

	/** Default blue {@code Color}. */
	public static final @NonNull Color BLUE = new Color(BYTE_MIN, BYTE_MIN, BYTE_MAX);

	private byte red;
	private byte green;
	private byte blue;
	private byte alpha;

	/**
	 * Constructor with default value 0 for the alpha channel
	 * @param red   value of red in range [0-255]
	 * @param green value of green in range [0-255]
	 * @param blue  value of blue in range [0-255]
	 */
	public Color(final byte red, final byte green, final byte blue) {
		this(red, green, blue, BYTE_MIN);
	}

	/**
	 * Creates human-readable description.
	 * @return description of the current object
	 */
	public @NonNull String toPrettyString() {
		return traceNonNull(Messages.ToString.COLOR(
			Byte.toUnsignedInt(red),
			Byte.toUnsignedInt(green),
			Byte.toUnsignedInt(blue),
			Byte.toUnsignedInt(alpha)));
	}

}
