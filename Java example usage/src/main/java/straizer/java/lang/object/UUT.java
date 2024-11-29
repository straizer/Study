package straizer.java.lang.object;

/**
 * Example usage of {@link Object} class.
 *
 * @author straizer
 */
public final class UUT implements Cloneable {

	private final int n;

	/**
	 * Creates an instance of {@link UUT}.
	 *
	 * @param n value to hold
	 */
	public UUT(final int n) {
		this.n = n;
	}

	@Override
	public int hashCode() {
		return n;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof final UUT otherTest))
			return false;
		return n == otherTest.n;
	}

	@Override
	public UUT clone() throws CloneNotSupportedException {
		return (UUT) super.clone();
	}

	@Override
	public String toString() {
		return "UUT[n=" + n + "]";
	}
}
