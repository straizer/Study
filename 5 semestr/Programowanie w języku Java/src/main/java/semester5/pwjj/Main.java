package semester5.pwjj;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.entities.Color;
import semester5.pwjj.entities.shapes.Shape;
import semester5.pwjj.entities.shapes.impl.Rectangle;
import semester5.pwjj.entities.shapes.impl.Triangle;

import java.util.List;

/** Application entry point class. */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Main {

	/**
	 * Application entry point.
	 * @param ignoredArgs console arguments
	 */
	public static void main(final @NonNull String @NonNull [] ignoredArgs) {
		final @NonNull List<Shape> shapes = List.of(
			new Rectangle(1.0, 2.0, Color.RED),
			new Triangle(3.0, 4.0, 5.0, Color.GREEN)
		);

		shapes.forEach(it -> log.info(it.toString()));
	}
}
