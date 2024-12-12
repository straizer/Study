package semester5.pwjj.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.Representative;
import semester5.pwjj.utils.extensions.ExceptionUtils;

/** Interface defining transactional {@link EntityManager}. */
@ExtensionMethod(ExceptionUtils.class)
public interface TransactionalEntityManager extends EntityManager, Representative {

	/** Commits the current transaction. */
	void commitTransaction();

	/**
	 * This method always throws.
	 * Transactions in this class are handled internally.
	 * Use `commitTransaction()` to commit the transaction, otherwise it'll be rolled back.
	 * @throws UnsupportedOperationException always.
	 */
	@Override
	default void joinTransaction() {
		throwTransactionException();
	}

	/**
	 * This method always throws.
	 * Transactions in this class are handled internally.
	 * Use `commitTransaction()` to commit the transaction, otherwise it'll be rolled back.
	 * @throws UnsupportedOperationException always.
	 */
	@Override
	default boolean isJoinedToTransaction() {
		throwTransactionException();
		return false;
	}

	/**
	 * This method always throws.
	 * Transactions in this class are handled internally.
	 * Use `commitTransaction()` to commit the transaction, otherwise it'll be rolled back.
	 * @throws UnsupportedOperationException always.
	 */
	@Override
	default @Nullable EntityTransaction getTransaction() {
		throwTransactionException();
		return null;
	}

	/**
	 * Throws exception when calling {@link EntityManager} methods related to transactions directly.
	 * Transactions in this class are handled internally.
	 * Use `commitTransaction()` to commit the transaction, otherwise it'll be rolled back.
	 * @throws UnsupportedOperationException always.
	 */
	private void throwTransactionException() {
		Messages.Error.TRANSACTIONS_HANDLED_INTERNALLY(getClass()).warnAndThrow(UnsupportedOperationException.class);
	}
}
