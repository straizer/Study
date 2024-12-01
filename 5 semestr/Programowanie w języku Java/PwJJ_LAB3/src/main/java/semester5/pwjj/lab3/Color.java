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

	private static final byte BYTE_MIN = 0;
	private static final byte BYTE_MAX = -1;

	/**
	 * Default red color.
	 */
	public static final Color RED = new Color(BYTE_MAX, BYTE_MIN, BYTE_MIN);

	/**
	 * Default green color.
	 */
	public static final Color GREEN = new Color(BYTE_MIN, BYTE_MAX, BYTE_MIN);

	/**
	 * Default blue color.
	 */
	public static final Color BLUE = new Color(BYTE_MIN, BYTE_MIN, BYTE_MAX);

	/**
	 * Constructor with default value 0 for the alpha channel
	 *
	 * @param red   value of red in range [0-255]
	 * @param green value of green in range [0-255]
	 * @param blue  value of blue in range [0-255]
	 */
	public Color(final byte red, final byte green, final byte blue) {
		this(red, green, blue, BYTE_MIN);
	}

	/**
	 * Creates human-readable description.
	 *
	 * @return description of the current object
	 */
	@Override
	public String toString() {
		return String.format(
			Messages.get("toString.color"),
			Byte.toUnsignedInt(red),
			Byte.toUnsignedInt(green),
			Byte.toUnsignedInt(blue),
			Byte.toUnsignedInt(alpha));
	}
}
