package semester5.pwjj.dao.impl;

import lombok.ToString;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.dao.AbstractDAO;
import semester5.pwjj.entities.shapes.Shape;
import semester5.pwjj.utils.extensions.TraceableUtils;

/**
 * Data Access Object (DAO) for handling operations related to the {@link Shape} entity.
 * This class provides functionality for interacting with the database or persistent storage for {@link Shape} objects.
 * It extends {@link AbstractDAO} with {@link Shape} as the entity type.
 */
@SuppressWarnings("ClassWithoutLogger")
@ToString
public final class ShapeDAO extends AbstractDAO<@NonNull Shape> {

	/**
	 * Constructs a new instance of the {@code ShapeDAO}.
	 * This constructor is responsible for initializing the DAO for the {@link Shape} entity
	 * by passing the {@link Shape} class to the superclass {@link AbstractDAO}.
	 */
	public ShapeDAO() {
		super(Shape.class);
		//noinspection ThisEscapedInObjectConstruction
		TraceableUtils.traceConstructor(this);
	}
}
