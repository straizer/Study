package semester5.pwjj.dao;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.hibernate.HibernateException;
import semester5.pwjj.Representative;
import semester5.pwjj.utils.HibernateSession;
import semester5.pwjj.utils.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * {@link DAO} (Data Access Object) common implementation.
 * @param <T> type of data that {@link DAO} handles
 */
@Slf4j
@ToString
@RequiredArgsConstructor
@ExtensionMethod({StringUtils.class, Objects.class})
public abstract class AbstractDAO<T extends Representative> implements DAO<T>, Representative {

	private final @NonNull Class<T> handledClass;

	@Override
	public void create(final @NonNull T entity) {
		try (final HibernateSession session = new HibernateSession()) {
			session.persist(entity);
			session.commitTransaction();
		} catch (final HibernateException _) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED("create")); //NON-NLS
			return;
		debugMessage("Saving in", entity); //NON-NLS
		} catch (final EntityExistsException _) {
			log.warn(Messages.Error.ENTITY_ALREADY_EXISTS(handledClass, entity));
			return;
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE(handledClass, entity));
			return;
		}
		debugMessage("Saved in", entity); //NON-NLS
	}

	@Override
	public @NonNull Optional<T> read(final int id) {
		final @NonNull Optional<T> entity;
		try (final HibernateSession session = new HibernateSession()) {
			entity = Optional.ofNullable(session.find(handledClass, id));
			session.commitTransaction();
		} catch (final HibernateException _) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED("read")); //NON-NLS
			return Optional.empty();
		debugMessage("Getting from", id); //NON-NLS
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE(handledClass, null));
			return Optional.empty();
		}
		if (entity.isEmpty()) {
			debugMessage("Not found in", id); //NON-NLS
		} else {
			debugMessage("Found in", entity.get()); //NON-NLS
		}
		return traceNonNull(entity);
	}

	@Override
	public @NonNull List<T> readAll() {
		final @NonNull List<T> entities;
		try (final HibernateSession session = new HibernateSession()) {
		debugMessage("Getting from", "all entities"); //NON-NLS
			final CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(handledClass);
			query.from(handledClass);
			entities = session.createQuery(query).list();
			session.commitTransaction();
		} catch (final HibernateException _) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED("readAll")); //NON-NLS
			return List.of();
		}
		debugMessage("Found in", "%s entities".safeFormat(entities.size())); //NON-NLS
		return traceNonNull(entities);
	}

	@Override
	public @NonNull Optional<T> update(final @NonNull T entity) {
		final @NonNull Optional<T> updatedEntity;
		try (final HibernateSession session = new HibernateSession()) {
			updatedEntity = Optional.ofNullable(session.merge(entity));
			session.commitTransaction();
		} catch (final HibernateException _) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED("update")); //NON-NLS
			return Optional.empty();
		debugMessage("Updating in", entity); //NON-NLS
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE_OR_REMOVED(handledClass, entity));
			return Optional.empty();
		}
		debugMessage("Updated in", entity); //NON-NLS
		return traceNonNull(updatedEntity);
	}

	@Override
	public void delete(final int id) {
		try (final HibernateSession session = new HibernateSession()) {
			session.remove(session.find(handledClass, id));
			session.commitTransaction();
		} catch (final HibernateException _) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED("delete")); //NON-NLS
			return;
		debugMessage("Removing from", id); //NON-NLS
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE(handledClass, null));
			return;
		}
		debugMessage("Removed from", id); //NON-NLS
	}

	private void debugMessage(final @NonNull String prefix, final @NonNull T entity) {
		debugMessageInternal(prefix, ": %s".safeFormat(entity)); //NON-NLS
	}

	private void debugMessage(final @NonNull String prefix, final int id) {
		debugMessageInternal(prefix, " with id <%d>".safeFormat(id)); //NON-NLS
	}

	private void debugMessage(final @NonNull String prefix, final @NonNull String infix) {
		debugMessageInternal(prefix, infix, StringUtils.EMPTY); //NON-NLS
	}

	private void debugMessageInternal(final @NonNull String prefix, final @NonNull String suffix) {
		debugMessageInternal(prefix, "entity", suffix); //NON-NLS
	}

	private void debugMessageInternal(
		final @NonNull String prefix, final @NonNull String infix, final @NonNull String suffix
	) {
		log.debug("{} persistence {} of type <{}>{}", prefix, infix, handledClass, suffix); //NON-NLS
	}
}
