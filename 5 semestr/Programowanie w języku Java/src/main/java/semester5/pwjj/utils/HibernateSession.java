package semester5.pwjj.utils;

import jakarta.persistence.PersistenceException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import semester5.pwjj.Representative;

import java.util.Objects;

/** Class managing Hibernate session. */
@Slf4j
@ToString
public final class HibernateSession implements AutoCloseable, Representative {

	@Getter(value = AccessLevel.PRIVATE, lazy = true)
	private static final SessionFactory sessionFactory = initializeSessionFactory();

	@Delegate(excludes = NotDelegated.class)
	private final @NonNull Session session;

	/**
	 * Creates {@link AutoCloseable} Hibernate session and starts transaction.
	 * @throws HibernateException if the `hibernate.cfg.xml` file is missing or invalid,
	 *                            or opening {@link Session} fails.
	 */
	public HibernateSession() {
		final SessionFactory _sessionFactory = getSessionFactory();
		log.debug("Opening session"); //NON-NLS
		try {
			session = _sessionFactory.openSession();
		} catch (final HibernateException ex) {
			log.warn(Messages.Error.OPEN_SESSION_FAILED(ex));
			throw ex;
		}
		log.debug("Session opened: {}", session); //NON-NLS
		traceCtor();
		beginTransaction();
	}

	/**
	 * Initializes {@code sessionFactory}.
	 * @throws HibernateException if the `hibernate.cfg.xml` file is missing or invalid.
	 */
	private static @NonNull SessionFactory initializeSessionFactory() {
		log.debug("Initializing session factory"); //NON-NLS
		final Configuration configuration;
		try {
			configuration = new Configuration().configure();
		} catch (final HibernateException ex) {
			log.warn(Messages.Error.MISSING_HIBERNATE_CONFIG());
			throw ex;
		}
		final @NonNull SessionFactory _sessionFactory;
		try {
			_sessionFactory = configuration.buildSessionFactory();
		} catch (final HibernateException ex) {
			log.warn(Messages.Error.INVALID_HIBERNATE_CONFIG());
			throw ex;
		}
		log.debug("Session factory initialized: {}", _sessionFactory); //NON-NLS
		log.debug("Adding a shutdown hook destroying the session factory"); //NON-NLS
		try {
			Runtime.getRuntime().addShutdownHook(new Thread(HibernateSession::shutdown));
		} catch (final RuntimeException _) {
			log.warn(Messages.Error.CANNOT_ADD_SHUTDOWN_HOOK("HibernateSession::shutdown")); //NON-NLS
		}
		log.debug("Shutdown hook destroying the session factory added"); //NON-NLS
		return _sessionFactory;
	}

	/** Closes session factory. */
	private static void shutdown() {
		log.debug("Closing session factory: {}", getSessionFactory()); //NON-NLS
		try {
			Objects.requireNonNull(getSessionFactory()).close();
		} catch (final HibernateException ex) {
			log.warn(Messages.Error.CLOSE_SESSION_FACTORY_FAILED(ex));
		}
		log.debug("Session factory closed: {}", getSessionFactory()); //NON-NLS
	}

	/** Commits the current transaction. */
	public void commitTransaction() {
		log.debug("Commiting transaction: {}", session.getTransaction()); //NON-NLS
		try {
			session.getTransaction().commit();
		} catch (final RuntimeException ex) {
			log.warn(Messages.Error.COMMIT_FAILED(ex));
		}
		log.debug("Transaction commited: {}", session.getTransaction()); //NON-NLS
		beginTransaction();
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
	private void beginTransaction() {
		log.debug("Beginning transaction: {}", session.getTransaction()); //NON-NLS
		session.beginTransaction();
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
		@NonNull SessionFactory getSessionFactory();
		@NonNull Transaction beginTransaction();
		@NonNull Transaction getTransaction();
		boolean isJoinedToTransaction();
		void joinTransaction();
		void close();
	}
}
