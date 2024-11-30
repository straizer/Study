package semester5.pwjj.lab3;

/**
 * Record representing any color in RGBA format.
 *
 * @param red   value of red in range [0-255]
 * @param green value of green in range [0-255]
 * @param blue  value of blue in range [0-255]
 * @param alpha value of transparency in range [0-255]
 */
public record Color(byte red, byte green, byte blue, byte alpha) {

	/**
	 * Default red color.
	 */
	public static final Color RED = new Color((byte) 255, (byte) 0, (byte) 0);

	/**
	 * Default green color.
	 */
	public static final Color GREEN = new Color((byte) 0, (byte) 255, (byte) 0);

	/**
	 * Default blue color.
	 */
	public static final Color BLUE = new Color((byte) 0, (byte) 0, (byte) 255);

	/**
	 * Constructor with default value 0 for the alpha channel
	 *
	 * @param red   value of red in range [0-255]
	 * @param green value of green in range [0-255]
	 * @param blue  value of blue in range [0-255]
	 */
	public Color(final byte red, final byte green, final byte blue) {
		this(red, green, blue, (byte) 0);
	}

	@Override
	public String toString() {
		return String.format(
			"red={}, green={}, blue={}, alpha={}",
			Byte.toUnsignedInt(red),
			Byte.toUnsignedInt(green),
			Byte.toUnsignedInt(blue),
			Byte.toUnsignedInt(alpha));
	}
}
