package semester5.pwjj.utils;

import jakarta.persistence.EntityManager;
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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import semester5.pwjj.utils.extensions.ExceptionUtils;

/** Class managing Hibernate session. */
@Slf4j
@ToString
@ExtensionMethod(ExceptionUtils.class)
public final class HibernateEntityManager implements TransactionalEntityManager {

	@Delegate(excludes = NotDelegated.class)
	private final @NonNull EntityManager session;

	/**
	 * Creates {@link AutoCloseable} Hibernate entity manager and starts transaction.
	 * @throws HibernateException if the `hibernate.cfg.xml` file is missing or invalid,
	 *                            or opening {@link Session} fails.
	 */
	public HibernateEntityManager() {
		final @NonNull SessionFactory _sessionFactory = getSessionFactory();
		log.debug("Opening session"); //NON-NLS
		@Nullable EntityManager entityManager = null;
		try {
			entityManager = _sessionFactory.openSession();
		} catch (final HibernateException ex) {
			Messages.Error.OPEN_SESSION_FAILED(ex).warnAndThrow(ex);
		}
		session = entityManager;
		log.debug("Session opened: {}", session); //NON-NLS
		traceCtor();
		beginTransactionInternal();
	}

	/**
	 * Initializes {@code sessionFactory}.
	 * @throws HibernateException if the `hibernate.cfg.xml` file is missing or invalid.
	 */
	private static @NonNull SessionFactory initializeSessionFactory() {
		log.debug("Initializing session factory"); //NON-NLS
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
		log.debug("Session factory initialized: {}", _sessionFactory); //NON-NLS
		log.debug("Adding a shutdown hook destroying the session factory"); //NON-NLS
		try {
			Runtime.getRuntime().addShutdownHook(new Thread(HibernateEntityManager::shutdown));
		} catch (final RuntimeException _) {
			log.warn(Messages.Error.CANNOT_ADD_SHUTDOWN_HOOK("HibernateEntityManager::shutdown")); //NON-NLS
		}
		log.debug("Shutdown hook destroying the session factory added"); //NON-NLS
		return _sessionFactory;
	}

	/** Closes session factory. */
	private static void shutdown() {
		log.debug("Closing session factory: {}", getSessionFactory()); //NON-NLS
		try {
			getSessionFactory().close();
		} catch (final HibernateException ex) {
			log.warn(Messages.Error.CLOSE_SESSION_FACTORY_FAILED(ex));
		}
		log.debug("Session factory closed: {}", getSessionFactory()); //NON-NLS
	}

	@Override
	public void commitTransaction() {
		log.debug("Commiting transaction: {}", session.getTransaction()); //NON-NLS
		try {
			session.getTransaction().commit();
		} catch (final RuntimeException ex) {
			log.warn(Messages.Error.COMMIT_FAILED(ex));
		}
		log.debug("Transaction commited: {}", session.getTransaction()); //NON-NLS
		beginTransactionInternal();
	}

	@Override
	public void close() {
		log.debug("Closing session: {}", session); //NON-NLS
		rollbackTransaction();
		try {
			session.close();
		} catch (final HibernateException ex) {
			log.warn(Messages.Error.CLOSE_SESSION_FAILED(ex));
		}
		log.debug("Session closed: {}", session); //NON-NLS
	}

	/** Begins new transaction. */
	private void beginTransactionInternal() {
		log.debug("Beginning transaction: {}", session.getTransaction()); //NON-NLS
		session.getTransaction().begin();
		log.debug("New transaction began: {}", session.getTransaction()); //NON-NLS
	}

	/** Rolls back the current transaction. */
	private void rollbackTransaction() {
		log.debug("Rolling back transaction: {}", session.getTransaction()); //NON-NLS
		try {
			session.getTransaction().rollback();
		} catch (final PersistenceException ex) {
			log.warn(Messages.Error.ROLLBACK_FAILED(ex));
		}
		log.debug("Transaction rolled back: {}", session.getTransaction()); //NON-NLS
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
	private static final SessionFactory sessionFactory = initializeSessionFactory();
}
