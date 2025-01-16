package semester5.pwjj.utils.persistence;

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
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import semester5.pwjj.utils.extensions.ExceptionUtils;
import semester5.pwjj.utils.extensions.TraceableUtils;

/**
 * This class represents a Hibernate-based implementation of the {@link TransactionalEntityManager}.
 * It provides an {@link EntityManager} wrapper with internal transaction handling and ensures
 * that transaction management is abstracted away from the client code.
 * Transactions are automatically started when the entity manager is created and are expected
 * to be committed using the {@link #commitTransaction()} method.
 * Rollbacks are handled internally if exceptions occur.
 * <p>
 * Transaction-related methods from the {@link EntityManager} interface, such as {@link #getTransaction()},
 * {@link #joinTransaction()}, and {@link #isJoinedToTransaction()}, are disabled and shouldn't be used directly.
 * Closing the entity manager performs a rollback on the current transaction if necessary.
 */
@Slf4j
@ToString
@ExtensionMethod(ExceptionUtils.class)
public final class HibernateEntityManager implements TransactionalEntityManager {

	/**
	 * A lazily initialized static {@link EntityManagerFactory} instance used to create entity managers.
	 * This instance ensures that only a single {@link EntityManagerFactory} is created and reused throughout
	 * the application's lifecycle, ensuring efficient resource management.
	 * <p>
	 * The initialization logic for this {@link EntityManagerFactory} is encapsulated by the
	 * {@link #initializeEntityManagerFactory()} method.
	 */
	@Getter(value = AccessLevel.PRIVATE, lazy = true)
	private static final EntityManagerFactory staticEntityManagerFactory = initializeEntityManagerFactory();

	/**
	 * An instance of the {@link EntityManager} used for managing and database interactions.
	 * <p>
	 * The {@code entityManager} field is annotated with {@link Delegate}, indicating that its methods
	 * can be accessed from the enclosing instance while excluding those defined in {@link NotDelegated}.
	 * This provides a seamless delegation mechanism while restricting functionality deemed
	 * inappropriate or unnecessary for delegation.
	 * <p>
	 * It acts as the central interface for managing persistent entities within a persistence context.
	 */
	@Delegate(excludes = NotDelegated.class)
	private final @NonNull EntityManager entityManager;

	/**
	 * Constructs a new instance of {@code HibernateEntityManager}.
	 * If session creation fails due to a {@link HibernateException}, it logs the error and throws that exception.
	 * After successful instance creation, creates a new transaction that's ready to use.
	 * @throws HibernateException if the {@code hibernate.cfg.xml} file is missing or invalid,
	 *                            or creating of {@link EntityManager} fails.
	 */
	@SuppressWarnings({"CallToSimpleGetterFromWithinClass", "argument"})
	public HibernateEntityManager() {
		final @NonNull SessionFactory _sessionFactory = (SessionFactory) getStaticEntityManagerFactory();
		log.debug("Creating entity manager"); //NON-NLS
		try {
			//noinspection HibernateResourceOpenedButNotSafelyClosed
			entityManager = _sessionFactory.openSession();
		} catch (final HibernateException ex) {
			throw Messages.Error.OPEN_ENTITY_MANAGER_FAILED(ex).warnAndReturn(ex);
		}
		log.debug("Entity manager created: {}", entityManager); //NON-NLS
		//noinspection ThisEscapedInObjectConstruction
		TraceableUtils.traceConstructor(this);
		beginTransactionInternal();
	}

	/**
	 * Initializes and configures a Hibernate {@link SessionFactory} using the configured Hibernate settings file
	 * {@code hibernate.cfg.xml}.
	 * @return a {@link SessionFactory} instance, configured and ready for use
	 * @throws HibernateException if the {@code hibernate.cfg.xml} file is missing or invalid
	 */
	private static @NonNull SessionFactory initializeEntityManagerFactory() {
		log.debug("Initializing entity manager factory"); //NON-NLS
		final @MonotonicNonNull Configuration configuration;
		try {
			configuration = new Configuration().configure();
		} catch (final HibernateException ex) {
			throw Messages.Error.MISSING_HIBERNATE_CONFIG().warnAndReturn(ex);
		}
		final @MonotonicNonNull SessionFactory sessionFactory;
		try {
			//noinspection resource
			sessionFactory = configuration.buildSessionFactory();
		} catch (final HibernateException ex) {
			throw Messages.Error.INVALID_HIBERNATE_CONFIG().warnAndReturn(ex);
		}
		log.debug("Entity manager factory initialized: {}", sessionFactory); //NON-NLS
		addShutdownHook();
		return sessionFactory;
	}

	/**
	 * Adds a shutdown hook to the runtime environment that executes the shutdown logic
	 * for the {@link EntityManagerFactory}.
	 * This ensures that {@link EntityManagerFactory} is cleaned up when the application is terminated.
	 */
	private static void addShutdownHook() {
		log.debug("Adding a shutdown hook destroying entity manager factory"); //NON-NLS
		try {
			Runtime.getRuntime().addShutdownHook(new Thread(HibernateEntityManager::shutdown));
		} catch (final RuntimeException _) {
			log.warn(Messages.Error.CANNOT_ADD_SHUTDOWN_HOOK("HibernateEntityManager::shutdown")); //NON-NLS
		}
		log.debug("Shutdown hook destroying the entity manager factory added"); //NON-NLS
	}

	/**
	 * Safely shuts down the static {@link EntityManagerFactory}.
	 * This method is designed to ensure graceful cleanup of resources and proper logging in case of errors.
	 */
	@SuppressWarnings("CallToSimpleGetterFromWithinClass")
	private static void shutdown() {
		log.debug("Closing entity manager factory: {}", getStaticEntityManagerFactory()); //NON-NLS
		try {
			getStaticEntityManagerFactory().close();
		} catch (final HibernateException ex) {
			log.warn(Messages.Error.CLOSE_ENTITY_MANAGER_FACTORY_FAILED(ex));
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
			log.warn(Messages.Error.CLOSE_ENTITY_MANAGER_FAILED(ex));
		}
		log.debug("Entity manager closed: {}", entityManager); //NON-NLS
	}

	/** Initiates a new transaction using the {@code entityManager}. */
	private void beginTransactionInternal() {
		log.debug("Beginning transaction: {}", entityManager.getTransaction()); //NON-NLS
		entityManager.getTransaction().begin();
		log.debug("New transaction began: {}", entityManager.getTransaction()); //NON-NLS
	}

	/**
	 * Rolls back the current transaction associated with the {@code entityManager}.
	 * If an exception occurs during the rollback operation, it is logged as a warning
	 * using a message indicating a rollback failure.
	 */
	private void rollbackTransaction() {
		log.debug("Rolling back transaction: {}", entityManager.getTransaction()); //NON-NLS
		try {
			entityManager.getTransaction().rollback();
		} catch (final PersistenceException ex) {
			log.warn(Messages.Error.ROLLBACK_FAILED(ex));
		}
		log.debug("Transaction rolled back: {}", entityManager.getTransaction()); //NON-NLS
	}

	/** Interface representing signatures of methods that should be not delegated to {@code entityManager} object. */
	@SuppressWarnings({"InterfaceNeverImplemented", "unused"})
	private interface NotDelegated {
		//@formatter:off
		@NonNull EntityTransaction getTransaction();
		boolean isJoinedToTransaction();
		void joinTransaction();
		void close();
		//@formatter:on
	}
}
