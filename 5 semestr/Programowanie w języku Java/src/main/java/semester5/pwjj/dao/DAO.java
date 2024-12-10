package semester5.pwjj.dao;

import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.Representative;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface.
 * @param <T> type of data that {@code DAO} handles
 */
public interface DAO<T extends Representative> {

	/**
	 * Stores an object of type {@code T} in persistent storage.
	 * @param entity object to store
	 */
	void create(final @NonNull T entity);

	/**
	 * Retrieves an object of type {@code T} from persistent storage.
	 * @param id object id to retrieve
	 * @return persistent object
	 */
	@NonNull
	Optional<T> read(final int id);

	/**
	 * Retrieves all objects of type {@code T} from persistent storage.
	 * @return list of persistent objects
	 */
	@NonNull
	List<T> readAll();

	/**
	 * Updates an object of type {@code T} in persistent storage.
	 * @param entity object to store
	 * @return persistent object
	 */
	@NonNull
	Optional<T> update(final @NonNull T entity);

	/**
	 * Deletes an object of type {@code T} from persistent storage.
	 * @param id object id to delete
	 */
	void delete(final int id);
}
