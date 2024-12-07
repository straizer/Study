package semester5.pwjj.entities;

import jakarta.persistence.Embeddable;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.utils.ReturnLogger;
import semester5.pwjj.utils.StringUtils;

/**
 * Record representing any color in RGBA format.
 * @param red   value of red in range [0-255]
 * @param green value of green in range [0-255]
 * @param blue  value of blue in range [0-255]
 * @param alpha value of transparency in range [0-255]
 */
@Embeddable
public record Color(byte red, byte green, byte blue, byte alpha) implements ReturnLogger {

	private static final byte BYTE_MIN = 0;
	private static final byte BYTE_MAX = -1;

	/** Default red {@code Color}. */
	public static final @NonNull Color RED = new Color(BYTE_MAX, BYTE_MIN, BYTE_MIN);

	/** Default green {@code Color}. */
	public static final @NonNull Color GREEN = new Color(BYTE_MIN, BYTE_MAX, BYTE_MIN);

	/** Default blue {@code Color}. */
	public static final @NonNull Color BLUE = new Color(BYTE_MIN, BYTE_MIN, BYTE_MAX);

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
	@Override
	public @NonNull String toString() {
		return traceNonNull(Messages.ToString.COLOR(
			Byte.toUnsignedInt(red),
			Byte.toUnsignedInt(green),
			Byte.toUnsignedInt(blue),
			Byte.toUnsignedInt(alpha)));
	}

	@Override
	public @NonNull String _repr() {
		return StringUtils.format("Color(red=%d, green=%d, blue=%d, alpha=%d)", red, green, blue, alpha); //NON-NLS
	}
}
