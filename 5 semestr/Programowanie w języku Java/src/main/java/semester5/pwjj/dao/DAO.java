package semester5.pwjj.dao;

import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.utils.Traceable;

import java.util.List;
import java.util.Optional;

/**
 * A generic Data Access Object (DAO) interface providing standard operations
 * for managing persistent entities of type {@link T}.
 * @param <T> the type of objects that this DAO manages, which must extend {@link Traceable}.
 */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "InterfaceWithOnlyOneDirectInheritor", "unused"})
public interface DAO<@NonNull T extends @NonNull Traceable> {

	/**
	 * Persists the specified entity in the persistent storage.
	 * @param entity the object of type {@link T} to be saved
	 */
	void create(final @NonNull T entity);

	/**
	 * Retrieves an entity of type {@link T} by its identifier from persistent storage.
	 * @param id the unique identifier of the entity to retrieve from storage
	 * @return an {@link Optional} containing the persistent entity of type {@link T} if found;
	 * otherwise, an empty {@link Optional}
	 */
	@NonNull
	Optional<@NonNull T> read(final int id);

	/**
	 * Retrieves all entities of type {@link T} from persistent storage.
	 * @return a {@link List} containing all persistent entities of type {@link T}
	 */
	@NonNull
	List<@NonNull T> readAll();

	/**
	 * Updates the entity specified by its identifier in the persistent storage.
	 * @param entity the entity of type {@link T} to be updated
	 * @return an {@link Optional} containing the updated entity if the update was successful;
	 * otherwise an empty {@link Optional} if the entity couldn't be updated
	 */
	@NonNull
	Optional<@NonNull T> update(final @NonNull T entity);

	/**
	 * Deletes an entity of type {@link T} by its identifier from the persistent storage.
	 * @param id the unique identifier of the entity to delete from storage
	 */
	void delete(final int id);
}
