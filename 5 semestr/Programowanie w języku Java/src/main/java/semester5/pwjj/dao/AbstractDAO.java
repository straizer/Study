package semester5.pwjj.dao;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.hibernate.HibernateException;
import semester5.pwjj.Representative;
import semester5.pwjj.utils.HibernateSession;

import java.util.List;
import java.util.Optional;

/**
 * {@link DAO} (Data Access Object) common implementation.
 * @param <T> type of data that {@link DAO} handles
 */
@Slf4j
@RequiredArgsConstructor
@ToString
public abstract class AbstractDAO<T extends Representative> implements DAO<T>, Representative {

	private final @NonNull Class<T> handledClass;

	@Override
	public void create(final @NonNull T entity) {
		log.debug("Saving in persistence entity of type <{}>: {}", handledClass, entity); //NON-NLS
		try (final HibernateSession session = new HibernateSession()) {
			session.persist(entity);
			session.commitTransaction();
		} catch (final HibernateException _) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED("create")); //NON-NLS
			return;
		} catch (final EntityExistsException _) {
			log.warn(Messages.Error.ENTITY_ALREADY_EXISTS(handledClass, entity));
			return;
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE(handledClass, entity));
			return;
		}
		log.debug("Saved in persistence entity of type <{}>: {}", handledClass, entity); //NON-NLS
	}

	@Override
	public @NonNull Optional<T> read(final int id) {
		log.debug("Getting from persistence entity of type <{}> with id <{}>", handledClass, id); //NON-NLS
		final @NonNull Optional<T> entity;
		try (final HibernateSession session = new HibernateSession()) {
			entity = Optional.ofNullable(session.find(handledClass, id));
			session.commitTransaction();
		} catch (final HibernateException _) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED("read")); //NON-NLS
			return Optional.empty();
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE(handledClass, null));
			return Optional.empty();
		}
		if (entity.isEmpty()) {
			log.debug("Not found in persistence entity of type <{}> with id <{}>", handledClass, id); //NON-NLS
		} else {
			log.debug("Found persisted entity of type <{}>: {}", handledClass, entity.get()); //NON-NLS
		}
		return traceNonNull(entity);
	}

	@Override
	public @NonNull List<T> readAll() {
		log.debug("Getting from persistence all entities of type <{}>", handledClass); //NON-NLS
		final @NonNull List<T> entities;
		try (final HibernateSession session = new HibernateSession()) {
			final CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(handledClass);
			query.from(handledClass);
			entities = session.createQuery(query).list();
			session.commitTransaction();
		} catch (final HibernateException _) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED("readAll")); //NON-NLS
			return List.of();
		}
		log.debug("Found {} persisted entities of type <{}>", entities.size(), handledClass); //NON-NLS
		return traceNonNull(entities);
	}

	@Override
	public @NonNull Optional<T> update(final @NonNull T entity) {
		log.debug("Updating in persistence entity of type <{}>: {}", handledClass, entity); //NON-NLS
		final @NonNull Optional<T> updatedEntity;
		try (final HibernateSession session = new HibernateSession()) {
			updatedEntity = Optional.ofNullable(session.merge(entity));
			session.commitTransaction();
		} catch (final HibernateException _) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED("update")); //NON-NLS
			return Optional.empty();
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE_OR_REMOVED(handledClass, entity));
			return Optional.empty();
		}
		log.debug("Updated in persistence entity of type <{}>: {}", handledClass, entity); //NON-NLS
		return traceNonNull(updatedEntity);
	}

	@Override
	public void delete(final int id) {
		log.debug("Removing from persistence entity of type <{}> with id <{}>", handledClass, id); //NON-NLS
		try (final HibernateSession session = new HibernateSession()) {
			session.remove(session.find(handledClass, id));
			session.commitTransaction();
		} catch (final HibernateException _) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED("delete")); //NON-NLS
			return;
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE(handledClass, null));
			return;
		}
		log.debug("Removed from persistence entity of type <{}> with id <{}>", handledClass, id); //NON-NLS
	}
}
