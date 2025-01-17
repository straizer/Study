package semester5.pwjj.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import semester5.pwjj.utils.Traceable;

/**
 * Represents an immutable RGBA color model.
 * <p>
 * Each color consists of four channels: red, green, blue and alpha (transparency),
 * each represented as a signed byte with a range of 0 to 255.
 * This class provides predefined constants for the primary colors (RED, GREEN and BLUE).
 * <p>
 * This class is intended to be embedded as part of another JPA {@link Entity}.
 */
@SuppressWarnings("ClassWithoutLogger")
@Embeddable
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public final class Color implements Traceable {

	/** Represents the minimum allowable value for a channel in the RGBA format. */
	private static final byte BYTE_MIN = 0;

	/** Represents the maximum allowable value for a channel in the RGBA format. */
	private static final byte BYTE_MAX = -1;

	/**
	 * Represents the predefined {@code RED} color constant, with its red channel set to maximum intensity,
	 * the green and blue channels set to minimum intensity, and the alpha channel set to maximum opacity.
	 */
	@SuppressWarnings("UseOfConcreteClass")
	public static final Color RED = new Color(BYTE_MAX, BYTE_MIN, BYTE_MIN);

	/**
	 * Represents the predefined {@code GREEN} color constant, with its green channel set to maximum intensity,
	 * the red and blue channels set to minimum intensity, and the alpha channel set to maximum opacity.
	 */
	@SuppressWarnings("UseOfConcreteClass")
	public static final Color GREEN = new Color(BYTE_MIN, BYTE_MAX, BYTE_MIN);

	/**
	 * Represents the predefined {@code BLUE} color constant, with its blue channel set to maximum intensity,
	 * the red and green channels set to minimum intensity, and the alpha channel set to maximum opacity.
	 */
	@SuppressWarnings("UseOfConcreteClass")
	public static final Color BLUE = new Color(BYTE_MIN, BYTE_MIN, BYTE_MAX);

	/**
	 * Represents the intensity of the red color channel in the RGBA format.
	 * Values are stored as a signed byte and range from {@code 0} to {@code 255}.
	 * A value of {@code 0} represents no intensity, while {@code 255} represents full intensity.
	 * This field is immutable and forms part of the overall color definition.
	 */
	byte red;

	/**
	 * Represents the intensity of the green color channel in the RGBA format.
	 * Values are stored as a signed byte and range from {@code 0} to {@code 255}.
	 * A value of {@code 0} represents no intensity, while {@code 255} represents full intensity.
	 * This field is immutable and forms part of the overall color definition.
	 */
	byte green;

	/**
	 * Represents the intensity of the blue color channel in the RGBA format.
	 * Values are stored as a signed byte and range from {@code 0} to {@code 255}.
	 * A value of {@code 0} represents no intensity, while {@code 255} represents full intensity.
	 * This field is immutable and forms part of the overall color definition.
	 */
	byte blue;

	/**
	 * Represents the alpha (transparency) channel in the RGBA color model.
	 * Values are stored as a signed byte and range from {@code 0} to {@code 255}.
	 * A value of {@code 0} indicates full transparency, while {@code 255} represents full opacity.
	 * This field is immutable and defines the transparency level of a color.
	 */
	byte alpha;

	/**
	 * Constructs a new {@code Color} object with the specified red, green and blue byte values.
	 * The alpha value is set to the maximum opacity by default.
	 * @param red   the red component of the color, in range of [0, 255]
	 * @param green the green component of the color, in range of [0, 255]
	 * @param blue  the blue component of the color, in range of [0, 255]
	 */
	public Color(final byte red, final byte green, final byte blue) {
		this(red, green, blue, BYTE_MAX);
	}

	/**
	 * Converts the object into a well-formatted, human-readable, i18nized {@link String} representation.
	 * @return a {@link String} representing the {@code Color} object
	 */
	public String toPrettyString() {
		return trace(Messages.ToString.COLOR(
			Byte.toUnsignedInt(red),
			Byte.toUnsignedInt(green),
			Byte.toUnsignedInt(blue),
			Byte.toUnsignedInt(alpha)));
	}

}
