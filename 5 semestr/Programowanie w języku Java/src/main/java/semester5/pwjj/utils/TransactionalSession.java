package semester5.pwjj.utils;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.Session;
import org.hibernate.Transaction;
import semester5.pwjj.utils.extensions.ExceptionUtils;

import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** Interface defining transactional {@link Session}. */
@ExtensionMethod(ExceptionUtils.class)
public interface TransactionalSession extends Session {

	/** Commits the current transaction. */
	void commitTransaction();

	/**
	 * This method always throws.
	 * Transactions in this class are handled internally.
	 * Use `commitTransaction()` to commit the transaction, otherwise it'll be rolled back.
	 * @throws UnsupportedOperationException always
	 */
	@Override
	default @Nullable Transaction beginTransaction() {
		throwTransactionException();
		return null;
	}

	/**
	 * This method always throws.
	 * Transactions in this class are handled internally.
	 * Use `commitTransaction()` to commit the transaction, otherwise it'll be rolled back.
	 * @throws UnsupportedOperationException always.
	 */
	@Override
	default @Nullable Transaction getTransaction() {
		throwTransactionException();
		return null;
	}

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
	 * This method always throws. Serialization isn't supported in a secure context.
	 * @throws NotSerializableException always.
	 */
	private void readObject(final ObjectInputStream in) throws NotSerializableException {
		throwSerializableException();
	}

	/**
	 * This method always throws. Serialization isn't supported in a secure context.
	 * @throws NotSerializableException always.
	 */
	private void writeObject(final ObjectOutputStream out) throws NotSerializableException {
		throwSerializableException();
	}

	/**
	 * Throws exception when calling {@link Session} methods related to transactions directly.
	 * Transactions in this class are handled internally.
	 * Use `commitTransaction()` to commit the transaction, otherwise it'll be rolled back.
	 * @throws UnsupportedOperationException always.
	 */
	private void throwTransactionException() {
		Messages.Error.TRANSACTIONS_HANDLED_INTERNALLY(getClass()).warnAndThrow(UnsupportedOperationException.class);
	}

	/**
	 * Throws exception when calling methods related to serialization.
	 * @throws NotSerializableException always.
	 */
	private void throwSerializableException() throws NotSerializableException {
		Messages.Error.CLASS_NOT_SERIALIZABLE(getClass()).warnAndThrow(NotSerializableException.class);
	}
}
