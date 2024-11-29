package straizer.java.lang.object;

/**
 * Example usage of {@link Object} class without implemented {@link Cloneable} interface.
 *
 * @author straizer
 */
public final class UUTNotCloneable {

	@Override
	public UUTNotCloneable clone() throws CloneNotSupportedException {
		return (UUTNotCloneable) super.clone();
	}
}
