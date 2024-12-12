package semester5.pwjj;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.dao.DAO;
import semester5.pwjj.dao.impl.ShapeDAO;
import semester5.pwjj.entities.Color;
import semester5.pwjj.entities.shapes.Shape;
import semester5.pwjj.entities.shapes.impl.Rectangle;
import semester5.pwjj.entities.shapes.impl.Triangle;

import java.util.List;

/** Application entry point class. */
@Slf4j
@UtilityClass
public class Main {

	/**
	 * Application entry point.
	 * @param ignoredArgs console arguments (ignored)
	 */
	public void main(final @NonNull String @NonNull [] ignoredArgs) {
		final @NonNull List<Shape> shapes = List.of(
			new Rectangle(1.0, 2.0, Color.RED),
			new Triangle(3.0, 4.0, 5.0, Color.GREEN)
		);

		final DAO<Shape> dao = new ShapeDAO();

		log.info("Calling toPrettyString");
		shapes.stream().map(Shape::toPrettyString).forEach(log::info);
		log.info("Calling toString");
		shapes.stream().map(Shape::toString).forEach(log::info);
		log.info("saving to DB");
		shapes.forEach(dao::create);
		log.info("reading from DB");
		dao.readAll().stream().map(Shape::toString).forEach(log::info);
	}
}
