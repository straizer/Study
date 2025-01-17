package semester5.pwjj.dao;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.utils.Traceable;
import semester5.pwjj.utils.extensions.ReflectionUtils;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.persistence.HibernateEntityManager;
import semester5.pwjj.utils.persistence.TransactionalEntityManager;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Abstract Data Access Object (DAO) that provides base implementations of the typical CRUD
 * (Create, Read, Update, Delete) operations for entities implementing the {@link Traceable} interface.
 * @param <T> The type parameter, extending {@link Traceable}, representing the managed entity.
 */
@SuppressWarnings({"AbstractClassWithOnlyOneDirectInheritor", "AbstractClassWithoutAbstractMethods"})
@Slf4j
@ToString
@RequiredArgsConstructor
@ExtensionMethod(StringUtils.class)
public abstract class AbstractDAO<T extends Traceable> implements DAO<T>, Traceable {

	/** Represents the class type, which must extend {@link Traceable}, of the objects to be managed. */
	private final Class<T> handledClass;

	/**
	 * Supplies {@code function} with newly created {@link TransactionalEntityManager} and executes it,
	 * then perform {@link TransactionalEntityManager#commitTransaction()}.
	 * @param function function to execute
	 * @param <T>      type of {@code function} return value
	 * @return an {@link Optional} of {@code function} return value if function succeeds; null otherwise
	 */
	@SuppressWarnings("annotations.on.use")
	private static <@Nullable T> @Nullable Optional<T> executeAndReturn(
		final Function<? super TransactionalEntityManager, ? extends @Nullable T> function
	) {
		final Optional<T> result;
		try (final TransactionalEntityManager entityManager = new HibernateEntityManager()) {
			result = Optional.ofNullable(function.apply(entityManager));
			entityManager.commitTransaction();
		} catch (final EntityExistsException | IllegalArgumentException ex) {
			throw ex;
		} catch (final PersistenceException _) {
			log.warn(Messages.Error.CREATE_ENTITY_MANAGER_FAILED(
				ReflectionUtils.getCallingMethodName("executeAndReturn", "execute"))); //NON-NLS
			//noinspection OptionalAssignedToNull,ReturnOfNull
			return null;
		}
		return result;
	}

	/**
	 * Supplies {@code function} with newly created {@link TransactionalEntityManager} and executes it,
	 * then perform {@link TransactionalEntityManager#commitTransaction()}.
	 * @param function function to execute
	 * @return an empty {@link Optional} if function succeeds; null otherwise
	 */
	@SuppressWarnings("annotations.on.use")
	private static <@Nullable T> @Nullable Optional<T> execute(
		final Consumer<? super TransactionalEntityManager> function
	) {
		return executeAndReturn(entityManager -> {
			function.accept(entityManager);
			return null;
		});
	}

	@Override
	public void create(final T entity) {
		debugMessageWithObject("Saving in", entity); //NON-NLS
		try {
			final @Nullable Optional<T> result = execute(
				entityManager -> entityManager.persist(entity));
			if (Objects.isNull(result)) { //NON-NLS
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
	public Optional<T> read(final int id) {
		debugMessageWithPrefixGettingFrom(id);
		final @Nullable Optional<T> entity;
		try {
			entity = executeAndReturn(entityManager -> entityManager.find(handledClass, id));
			if (Objects.isNull(entity)) {
				return Optional.empty();
			}
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE(handledClass, null));
			return Optional.empty();
		}
		if (entity.isEmpty()) {
			debugMessageWithId("Not found in", id); //NON-NLS
		} else {
			debugMessageWithPrefixFoundIn(entity.get());
		}
		return trace(entity);
	}

	@Override
	public List<T> readAll() {
		debugMessageWithPrefixGettingFrom("all entities"); //NON-NLS
		//noinspection OptionalContainsCollection
		final @Nullable Optional<List<T>> entitiesOptional;
		entitiesOptional = executeAndReturn(entityManager -> {
			final CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(handledClass);
			query.from(handledClass);
			return entityManager.createQuery(query).getResultList();
		});
		if (Objects.isNull(entitiesOptional)) {
			return List.of();
		}
		//noinspection OptionalGetWithoutIsPresent
		final List<T> entities = entitiesOptional.get();
		debugMessageWithPrefixFoundIn("%s entities".safeFormat(entities.size())); //NON-NLS
		return trace(entities);
	}

	@Override
	public Optional<T> update(final T entity) {
		debugMessageWithObject("Updating in", entity); //NON-NLS
		final @Nullable Optional<T> updatedEntity;
		try {
			updatedEntity = executeAndReturn(entityManager -> entityManager.merge(entity));
			if (Objects.isNull(updatedEntity)) {
				return Optional.empty();
			}
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE_OR_REMOVED(handledClass, entity));
			return Optional.empty();
		}
		//noinspection OptionalGetWithoutIsPresent
		debugMessageWithObject("Updated in", updatedEntity.get()); //NON-NLS
		return trace(updatedEntity);
	}

	@Override
	public void delete(final int id) {
		debugMessageWithId("Removing from", id); //NON-NLS
		try {
			final @Nullable Optional<T> result =
				execute(entityManager -> entityManager.remove(entityManager.find(handledClass, id)));
			if (Objects.isNull(result)) {
				return;
			}
		} catch (final IllegalArgumentException _) {
			log.warn(Messages.Error.NOT_AN_ENTITY_TYPE(handledClass, null));
			return;
		}
		debugMessageWithId("Removed from", id); //NON-NLS
	}

	/**
	 * Logs a debug message in the form of
	 * <ul>
	 *     <li>
	 *         {@code Found in persistence {object} of type <{handledClass}>}
	 *          - if {@code object} is of type {@link String}
	 *     </li>
	 *     <li>
	 *         {@code Found in persistence entity of type <{handledClass}>: {object}}
	 *          - if {@code object} is of type {@link Traceable}
	 *     </li>
	 * </ul>
	 * If no type is matched, logs warning and treats {@code object} as in {@link Traceable} case.
	 * @param object object to print (correct formating is used depending on its type)
	 */
	private void debugMessageWithPrefixFoundIn(final Object object) {
		final String prefix = "Found in"; //NON-NLS
		switch (object) {
			case final Traceable r -> debugMessageWithObject(prefix, r);
			case final String s -> debugMessageWithInfix(prefix, s);
			default -> {
				log.warn(
					Messages.Error.UNEXPECTED_TYPE(object.getClass().getSimpleName(), Traceable.class, String.class));
				debugMessageWithObject(prefix, object);
			}
		}
	}

	/**
	 * Logs a debug message in the form of
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
	private void debugMessageWithPrefixGettingFrom(final Object object) {
		final String prefix = "Getting from"; //NON-NLS
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
	 * Logs a debug message in the form of {@code {prefix} persistence entity of type <{handledClass}>: {entity}}.
	 * @param prefix prefix to insert
	 * @param entity entity to insert after calling {@link T#toString()}
	 */
	private void debugMessageWithObject(final String prefix, final Object entity) {
		debugMessage(prefix, ": %s".safeFormat(entity)); //NON-NLS
	}

	/**
	 * Logs a debug message in the form of {@code {prefix} persistence entity of type <{handledClass}> with id <{id}>}.
	 * @param prefix prefix to insert
	 * @param id     id to insert
	 */
	private void debugMessageWithId(final String prefix, final int id) {
		debugMessage(prefix, " with id <%d>".safeFormat(id)); //NON-NLS
	}

	/**
	 * Logs a debug message in the form of {@code {prefix} persistence {infix} of type <{handledClass}>}.
	 * @param prefix prefix to insert
	 * @param infix  infix to insert
	 */
	private void debugMessageWithInfix(final String prefix, final String infix) {
		debugMessage(prefix, infix, StringUtils.EMPTY);
	}

	/**
	 * Logs a debug message in the form of {@code {prefix} persistence entity of type <{handledClass}>{suffix}}.
	 * @param prefix prefix to insert
	 * @param suffix suffix to insert
	 */
	private void debugMessage(final String prefix, final String suffix) {
		debugMessage(prefix, "entity", suffix); //NON-NLS
	}

	/**
	 * Logs a debug message in the form of {@code {prefix} persistence {infix} of type <{handledClass}>{suffix}}.
	 * @param prefix prefix to insert
	 * @param infix  infix to insert
	 * @param suffix suffix to insert
	 */
	private void debugMessage(final String prefix, final String infix, final String suffix) {
		log.debug("{} persistence {} of type <{}>{}", prefix, infix, handledClass, suffix); //NON-NLS
	}
}
