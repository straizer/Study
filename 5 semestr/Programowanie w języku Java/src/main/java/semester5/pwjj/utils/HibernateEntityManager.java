package semester5.pwjj.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Delegate;
import lombok.experimental.ExtensionMethod;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import semester5.pwjj.utils.extensions.ExceptionUtils;

/** Class managing Hibernate session. */
@Slf4j
@ToString
@ExtensionMethod(ExceptionUtils.class)
public final class HibernateEntityManager implements TransactionalEntityManager {

	@Delegate(excludes = NotDelegated.class)
	private final @NonNull EntityManager entityManager;

	/**
	 * Creates {@link AutoCloseable} Hibernate entity manager and starts transaction.
	 * @throws HibernateException if the `hibernate.cfg.xml` file is missing or invalid,
	 *                            or creating {@link EntityManager} fails.
	 */
	public HibernateEntityManager() {
		final @NonNull SessionFactory _sessionFactory = (SessionFactory) getStaticEntityManagerFactory();
		log.debug("Creating entity manager"); //NON-NLS
		@Nullable EntityManager _entityManager = null;
		try {
			_entityManager = _sessionFactory.openSession();
		} catch (final HibernateException ex) {
			Messages.Error.OPEN_SESSION_FAILED(ex).warnAndThrow(ex);
		}
		entityManager = _entityManager;
		log.debug("Entity manager created: {}", entityManager); //NON-NLS
		traceCtor();
		beginTransactionInternal();
	}

	/**
	 * Initializes entity manager factory.
	 * @throws HibernateException if the `hibernate.cfg.xml` file is missing or invalid.
	 */
	private static @NonNull SessionFactory initializeEntityManagerFactory() {
		log.debug("Initializing entity manager factory"); //NON-NLS
		@Nullable Configuration configuration = null;
		try {
			configuration = new Configuration().configure();
		} catch (final HibernateException ex) {
			Messages.Error.MISSING_HIBERNATE_CONFIG().warnAndThrow(ex);
		}
		@Nullable SessionFactory _sessionFactory = null;
		try {
			_sessionFactory = configuration.buildSessionFactory();
		} catch (final HibernateException ex) {
			Messages.Error.INVALID_HIBERNATE_CONFIG().warnAndThrow(ex);
		}
		log.debug("Entity manager factory initialized: {}", _sessionFactory); //NON-NLS
		log.debug("Adding a shutdown hook destroying entity manager factory"); //NON-NLS
		try {
			Runtime.getRuntime().addShutdownHook(new Thread(HibernateEntityManager::shutdown));
		} catch (final RuntimeException _) {
			log.warn(Messages.Error.CANNOT_ADD_SHUTDOWN_HOOK("HibernateEntityManager::shutdown")); //NON-NLS
		}
		log.debug("Shutdown hook destroying the entity manager factory added"); //NON-NLS
		return _sessionFactory;
	}

	/** Closes entity manager factory. */
	private static void shutdown() {
		log.debug("Closing entity manager factory: {}", getStaticEntityManagerFactory()); //NON-NLS
		try {
			getStaticEntityManagerFactory().close();
		} catch (final HibernateException ex) {
			log.warn(Messages.Error.CLOSE_SESSION_FACTORY_FAILED(ex));
		}
		log.debug("Session entity manager closed: {}", getStaticEntityManagerFactory()); //NON-NLS
	}

	@Override
	public void commitTransaction() {
		log.debug("Commiting transaction: {}", entityManager.getTransaction()); //NON-NLS
		try {
			entityManager.getTransaction().commit();
		} catch (final RuntimeException ex) {
			log.warn(Messages.Error.COMMIT_FAILED(ex));
		}
		log.debug("Transaction commited: {}", entityManager.getTransaction()); //NON-NLS
		beginTransactionInternal();
	}

	@Override
	public void close() {
		log.debug("Closing entity manager: {}", entityManager); //NON-NLS
		rollbackTransaction();
		try {
			entityManager.close();
		} catch (final HibernateException ex) {
			log.warn(Messages.Error.CLOSE_SESSION_FAILED(ex));
		}
		log.debug("Entity manager closed: {}", entityManager); //NON-NLS
	}

	/** Begins new transaction. */
	private void beginTransactionInternal() {
		log.debug("Beginning transaction: {}", entityManager.getTransaction()); //NON-NLS
		entityManager.getTransaction().begin();
		log.debug("New transaction began: {}", entityManager.getTransaction()); //NON-NLS
	}

	/** Rolls back the current transaction. */
	private void rollbackTransaction() {
		log.debug("Rolling back transaction: {}", entityManager.getTransaction()); //NON-NLS
		try {
			entityManager.getTransaction().rollback();
		} catch (final PersistenceException ex) {
			log.warn(Messages.Error.ROLLBACK_FAILED(ex));
		}
		log.debug("Transaction rolled back: {}", entityManager.getTransaction()); //NON-NLS
	}

	/** Holds signatures of methods that should be not delegated to {@code session} object. */
	private interface NotDelegated {
		//@formatter:off
		@NonNull EntityTransaction getTransaction();
		boolean isJoinedToTransaction();
		void joinTransaction();
		void close();
		//@formatter:on
	}

	/** Get the session factory which created this session. */
	@Getter(value = AccessLevel.PRIVATE, lazy = true)
	private static final EntityManagerFactory staticEntityManagerFactory = initializeEntityManagerFactory();
}
