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
	 * Constructor with default value 0 for the alpha channel
	 *
	 * @param red   value of red in range [0-255]
	 * @param green value of green in range [0-255]
	 * @param blue  value of blue in range [0-255]
	 */
	public Color(final byte red, final byte green, final byte blue) {
		this(red, green, blue, (byte) 0);
	}
}
