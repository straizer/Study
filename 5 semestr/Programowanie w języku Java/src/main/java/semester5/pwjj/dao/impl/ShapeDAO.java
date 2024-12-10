package semester5.pwjj.dao.impl;

import lombok.ToString;
import semester5.pwjj.dao.AbstractDAO;
import semester5.pwjj.dao.DAO;
import semester5.pwjj.entities.shapes.Shape;

/** {@link DAO} (Data Access Object) implementation for {@link Shape}. */
@ToString
public final class ShapeDAO extends AbstractDAO<Shape> {

	/**
	 * Creates {@link DAO} (Data Access Object) for {@link Shape}.
	 */
	public ShapeDAO() {
		super(Shape.class);
		traceCtor();
	}
}
