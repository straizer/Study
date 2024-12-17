package semester5.pwjj.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.utils.extensions.ExceptionUtils;

/**
 * The {@code TransactionalEntityManager} interface extends {@link EntityManager},
 * providing transactional operations that are managed internally.
 * <p>
 * This interface allows for simplified transaction management, abstracting the complexities of initiating,
 * joining, or managing transactional states.
 * Transactions are controlled internally, and clients interact with the transaction only through the provided
 * {@link #commitTransaction()} method.
 * Any uncommitted transaction will be automatically rolled back on closure of the {@code TransactionalEntityManager}.
 * <p>
 * Several methods inherited from {@link EntityManager} are overridden to restrict direct transactional control,
 * and calling those methods will throw {@link UnsupportedOperationException}.
 */
@SuppressWarnings("InterfaceWithOnlyOneDirectInheritor")
@ExtensionMethod(ExceptionUtils.class)
public interface TransactionalEntityManager extends EntityManager, Traceable {

	/**
	 * Commits the current transaction managed internally by this {@code TransactionalEntityManager}.
	 * This method ensures that any operations performed after {@code TransactionalEntityManager} instance is created
	 * are persisted, and the transaction is successfully completed.
	 * <p>
	 * The transaction will be rolled back automatically if this method isn't invoked before the
	 * {@code TransactionalEntityManager} is closed.
	 * <p>
	 * This method should be called only after completing all operations that are intended to be part
	 * of the current transaction.
	 */
	void commitTransaction();

	/**
	 * This method always throws an {@link UnsupportedOperationException}.
	 * Transactions in this class are managed internally.
	 * To interact with transaction, use {@link #commitTransaction()}, otherwise it'll be rolled back.
	 * @throws UnsupportedOperationException always, with a message indicating that transactions are managed internally.
	 */
	@Override
	default void joinTransaction() {
		throwTransactionException();
	}

	/**
	 * This method always throws an {@link UnsupportedOperationException}.
	 * Transactions in this class are managed internally.
	 * To interact with transaction, use {@link #commitTransaction()}, otherwise it'll be rolled back.
	 * @return always {@code false}, as this method is disabled for direct use
	 * @throws UnsupportedOperationException always, with a message indicating that transactions are managed internally.
	 */
	@Override
	default boolean isJoinedToTransaction() {
		throwTransactionException();
		return false;
	}

	/**
	 * This method always throws an {@link UnsupportedOperationException}.
	 * Transactions in this class are managed internally.
	 * To interact with transaction, use {@link #commitTransaction()}, otherwise it'll be rolled back.
	 * @return always {@code null}, as this method is disabled for direct use
	 * @throws UnsupportedOperationException always, with a message indicating that transactions are managed internally
	 */
	@SuppressWarnings("override.return")
	@Override
	default @Nullable EntityTransaction getTransaction() {
		throwTransactionException();
		return null;
	}

	/**
	 * Throws an {@link UnsupportedOperationException} when directly invoking transaction-related methods
	 * of {@link EntityManager}.
	 * Transactions in this class are managed internally.
	 * To interact with transaction, use {@link #commitTransaction()}, otherwise it'll be rolled back.
	 * @throws UnsupportedOperationException always, with a message indicating that transactions are managed internally
	 */
	private void throwTransactionException() {
		throw Messages.Error.TRANSACTIONS_HANDLED_INTERNALLY(getClass())
			.warnAndReturn(UnsupportedOperationException.class);
	}
}
