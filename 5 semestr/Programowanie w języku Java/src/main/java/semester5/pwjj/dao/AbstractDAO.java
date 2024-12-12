package semester5.pwjj.dao;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.HibernateException;
import semester5.pwjj.Representative;
import semester5.pwjj.utils.HibernateSession;
import semester5.pwjj.utils.TransactionalSession;
import semester5.pwjj.utils.extensions.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

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

	/**
	 * Supplies {@code function} with newly created {@link TransactionalSession} and executes it,
	 * then perform {@link TransactionalSession#commitTransaction()}.
	 * @param function   function to execute
	 * @param methodName name of the calling method
	 * @param <T>        type of {@code function} return value
	 * @return {@link Optional} of {@code function} return value if function succeeds; null otherwise
	 */
	private static <T> @Nullable Optional<T> executeAndReturn(
		final @NonNull Function<? super TransactionalSession, ? extends T> function,
		final @NonNull String methodName
	) {
		final @NonNull Optional<T> result;
		try (final TransactionalSession session = new HibernateSession()) {
			result = Optional.ofNullable(function.apply(session));
			session.commitTransaction();
		} catch (final HibernateException _) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED(methodName)); //NON-NLS
			return null;
		}
		return result;
	}

	/**
	 * Supplies {@code function} with newly created {@link TransactionalSession} and executes it,
	 * then perform {@link TransactionalSession#commitTransaction()}.
	 * @param function   function to execute
	 * @param methodName name of the calling method
	 * @return Empty {@link Optional} if function succeeds; null otherwise
	 */
	private static <T> @Nullable Optional<T> execute(
		final @NonNull Consumer<? super TransactionalSession> function, final @NonNull String methodName
	) {
		return executeAndReturn(session -> {
				function.accept(session);
				return null;
			},
			methodName);
	}

	@Override
	public void create(final @NonNull T entity) {
		debugMessageWithObject("Saving in", entity); //NON-NLS
		try {
			if (execute(session -> session.persist(entity), "create").isNull()) { //NON-NLS
				return;
			}
		} catch (final EntityExistsException _) {
			log.warn(Messages.Error.ENTITY_ALREADY_EXISTS(handledClass, entity));
			return;
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE(handledClass, entity));
			return;
		}
		debugMessageWithObject("Saved in", entity); //NON-NLS
	}

	@Override
	public @NonNull Optional<T> read(final int id) {
		debugMessageWithPrefixGettingFrom(id); //NON-NLS
		final @Nullable Optional<T> entity;
		try {
			entity = executeAndReturn(session -> session.find(handledClass, id), "read"); //NON-NLS
			if (entity.isNull()) {
				return Optional.empty();
			}
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE(handledClass, null));
			return Optional.empty();
		}
		if (entity.isEmpty()) {
			debugMessageWithId("Not found in", id); //NON-NLS
		} else {
			debugMessageWithPrefixFoundIn(entity.get()); //NON-NLS
		}
		return traceNonNull(entity);
	}

	@Override
	public @NonNull List<T> readAll() {
		debugMessageWithPrefixGettingFrom("all entities"); //NON-NLS
		final @Nullable Optional<List<T>> entitiesOptional;
		entitiesOptional = executeAndReturn(session -> {
			final CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(handledClass);
			query.from(handledClass);
			return session.createQuery(query).list();
		}, "readAll"); //NON-NLS
		if (entitiesOptional.isNull()) {
			return List.of();
		}
		final @NonNull List<T> entities = entitiesOptional.get();
		debugMessageWithPrefixFoundIn("%s entities".safeFormat(entities.size())); //NON-NLS
		return traceNonNull(entities);
	}

	@Override
	public @NonNull Optional<T> update(final @NonNull T entity) {
		debugMessageWithObject("Updating in", entity); //NON-NLS
		final @Nullable Optional<T> updatedEntity;
		try {
			updatedEntity = executeAndReturn(session -> session.merge(entity), "update"); //NON-NLS
			if (updatedEntity.isNull()) {
				return Optional.empty();
			}
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE_OR_REMOVED(handledClass, entity));
			return Optional.empty();
		}
		debugMessageWithObject("Updated in", updatedEntity.get()); //NON-NLS
		return traceNonNull(updatedEntity);
	}

	@Override
	public void delete(final int id) {
		debugMessageWithId("Removing from", id); //NON-NLS
		try {
			if (execute(session -> session.remove(session.find(handledClass, id)), "delete").isNull()) { //NON-NLS
				return;
			}
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE(handledClass, null));
			return;
		}
		debugMessageWithId("Removed from", id); //NON-NLS
	}

	/**
	 * Prints a debug message in form of
	 * <ul>
	 *     <li>
	 *         {@code Found in persistence {object} of type <{handledClass}>}
	 *          - if {@code object} is of type {@link String}
	 *     </li>
	 *     <li>
	 *         {@code Found in persistence entity of type <{handledClass}>: {object}}
	 *          - if {@code object} is of type {@link Representative}
	 *     </li>
	 * </ul>
	 * If no type is matched, logs warning and treats {@code object} as in {@link Representative} case.
	 * @param object object to print (correct formating is used depending on its type)
	 */
	private void debugMessageWithPrefixFoundIn(final @NonNull Object object) {
		final @NonNull String prefix = "Found in"; //NON-NLS
		switch (object) {
			case final Representative r -> debugMessageWithObject(prefix, r);
			case final String s -> debugMessageWithInfix(prefix, s);
			default -> {
				log.warn(
					Messages.Error.UNEXPECTED_TYPE(
						object.getClass().getSimpleName(), Representative.class, String.class));
				debugMessageWithObject(prefix, object);
			}
		}
	}

	/**
	 * Prints a debug message in form of
	 * <ul>
	 *     <li>
	 *         {@code Getting from persistence {object} of type <{handledClass}>}
	 *          - if {@code object} is of type {@link String}
	 *     </li>
	 *     <li>
	 *         {@code Getting from persistence entity of type <{handledClass}> with id <{object}>}
	 *          - if {@code object} is of type {@link Integer}
	 *     </li>
	 * </ul>
	 * If no type is matched, logs warning, calls {@link Object#toString()} on {@code object}
	 * and treats it as in {@link String} case.
	 * @param object object to print (correct formating is used depending on its type)
	 */
	private void debugMessageWithPrefixGettingFrom(final @NonNull Object object) {
		final @NonNull String prefix = "Getting from"; //NON-NLS
		switch (object) {
			case final Integer i -> debugMessageWithObject(prefix, i);
			case final String s -> debugMessageWithInfix(prefix, s);
			default -> {
				log.warn(
					Messages.Error.UNEXPECTED_TYPE(object.getClass().getSimpleName(), Integer.class, String.class));
				debugMessageWithInfix(prefix, object.toString());
			}
		}
	}

	/**
	 * Prints a debug message in form of {@code {prefix} persistence entity of type <{handledClass}>: {entity}}.
	 * @param prefix prefix to insert
	 * @param entity entity to insert after calling {@link T#toString()}
	 */
	private void debugMessageWithObject(final @NonNull String prefix, final @NonNull Object entity) {
		debugMessage(prefix, ": %s".safeFormat(entity)); //NON-NLS
	}

	/**
	 * Prints a debug message in form of {@code {prefix} persistence entity of type <{handledClass}> with id <{id}>}.
	 * @param prefix prefix to insert
	 * @param id     id to insert
	 */
	private void debugMessageWithId(final @NonNull String prefix, final int id) {
		debugMessage(prefix, " with id <%d>".safeFormat(id)); //NON-NLS
	}

	/**
	 * Prints a debug message in form of {@code {prefix} persistence {infix} of type <{handledClass}>}.
	 * @param prefix prefix to insert
	 * @param infix  infix to insert
	 */
	private void debugMessageWithInfix(final @NonNull String prefix, final @NonNull String infix) {
		debugMessage(prefix, infix, StringUtils.EMPTY); //NON-NLS
	}

	/**
	 * Prints a debug message in form of {@code {prefix} persistence entity of type <{handledClass}>{suffix}}.
	 * @param prefix prefix to insert
	 * @param suffix suffix to insert
	 */
	private void debugMessage(final @NonNull String prefix, final @NonNull String suffix) {
		debugMessage(prefix, "entity", suffix); //NON-NLS
	}

	/**
	 * Prints a debug message in form of {@code {prefix} persistence {infix} of type <{handledClass}>{suffix}}.
	 * @param prefix prefix to insert
	 * @param infix  infix to insert
	 * @param suffix suffix to insert
	 */
	private void debugMessage(final @NonNull String prefix, final @NonNull String infix, final @NonNull String suffix) {
		log.debug("{} persistence {} of type <{}>{}", prefix, infix, handledClass, suffix); //NON-NLS
	}
}
