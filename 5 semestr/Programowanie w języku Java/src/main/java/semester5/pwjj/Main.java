package semester5.pwjj;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import semester5.pwjj.dao.DAO;
import semester5.pwjj.dao.impl.ShapeDAO;
import semester5.pwjj.entities.Color;
import semester5.pwjj.entities.shapes.Shape;
import semester5.pwjj.entities.shapes.impl.Rectangle;
import semester5.pwjj.entities.shapes.impl.Triangle;

import java.util.List;

/** Application entry point class. */
@SuppressWarnings("ClassUnconnectedToPackage")
@Slf4j
@UtilityClass
public class Main {

	/**
	 * Application entry point.
	 * @param ignoredArgs console arguments (ignored)
	 */
	@SuppressWarnings("DuplicatedCode")
	public void main(final String[] ignoredArgs) {
		final List<Shape> shapes = List.of(
			new Rectangle(1.0, 2.0, Color.RED),
			new Triangle(3.0, 4.0, 5.0, Color.GREEN)
		);

		final DAO<Shape> dao = new ShapeDAO();

		log.info("Calling toPrettyString"); //NON-NLS
		shapes.stream().map(Shape::toPrettyString).forEach(log::info);
		log.info("Calling toString"); //NON-NLS
		shapes.stream().map(Shape::toString).forEach(log::info);
		log.info("saving to DB"); //NON-NLS
		shapes.forEach(dao::create);
		log.info("reading from DB"); //NON-NLS
		dao.readAll().stream().map(Shape::toString).forEach(log::info);
	}
}
