package straizer.java.lang.object;

/**
 * Example usage of {@code Object} class without implemented {@code Cloneable} interface.
 *
 * @author straizer
 */
public final class UUTNotCloneable {

	@Override
	public UUTNotCloneable clone() throws CloneNotSupportedException {
		return (UUTNotCloneable) super.clone();
	}
}
