package semester5.pwjj.utils;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.utils.extensions.RepresentativeUtils;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code utils} in the current locale.
 * @implNote If different translation is required without changing default locale,
 * use {@link MessageProvider#get(I18nProperty i18nProperty, Locale locale)}
 */
@ExtensionMethod({StringUtils.class, RepresentativeUtils.class})
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code utils.error} messages. */
	public enum Error {
		;

		/** I18n key with value {@code utils.error.cannotAddShutdownHook}. */
		public static final @NonNull I18nProperty CANNOT_ADD_SHUTDOWN_HOOK =
			new UtilsI18nProperty("error.cannotAddShutdownHook");

		/** I18n key with value {@code utils.error.closeSessionFactoryFailed}. */
		public static final @NonNull I18nProperty CLOSE_SESSION_FACTORY_FAILED =
			new UtilsI18nProperty("error.closeSessionFactoryFailed");

		/** I18n key with value {@code utils.error.closeSessionFailed}. */
		public static final @NonNull I18nProperty CLOSE_SESSION_FAILED =
			new UtilsI18nProperty("error.closeSessionFailed");

		/** I18n key with value {@code utils.error.commitFailed}. */
		public static final @NonNull I18nProperty COMMIT_FAILED =
			new UtilsI18nProperty("error.commitFailed");

		/** I18n key with value {@code utils.error.invalidHibernateConfig}. */
		public static final @NonNull I18nProperty INVALID_HIBERNATE_CONFIG =
			new UtilsI18nProperty("error.invalidHibernateConfig");

		/** I18n key with value {@code utils.error.missingHibernateConfig}. */
		public static final @NonNull I18nProperty MISSING_HIBERNATE_CONFIG =
			new UtilsI18nProperty("error.missingHibernateConfig");

		/** I18n key with value {@code utils.error.openSessionFailed}. */
		public static final @NonNull I18nProperty OPEN_SESSION_FAILED =
			new UtilsI18nProperty("error.openSessionFailed");

		/** I18n key with value {@code utils.error.transactionsHandledInternally}. */
		public static final @NonNull I18nProperty TRANSACTIONS_HANDLED_INTERNALLY =
			new UtilsI18nProperty("error.transactionsHandledInternally");

		/** I18n key with value {@code utils.error.rollbackFailed}. */
		public static final @NonNull I18nProperty ROLLBACK_FAILED =
			new UtilsI18nProperty("error.rollbackFailed");

		/**
		 * I18n value retriever for key {@code utils.error.cannotAddShutdownHook}.
		 * @param methodName name of the method that failed to be added as shutdown hook
		 * @return value of key {@code utils.error.cannotAddShutdownHook}
		 */
		public static @NonNull String CANNOT_ADD_SHUTDOWN_HOOK(final @NonNull String methodName) {
			return CANNOT_ADD_SHUTDOWN_HOOK.getMessage().safeFormat(methodName).traceNonNull(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.closeSessionFactoryFailed}.
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.error.closeSessionFactoryFailed}
		 */
		public static @NonNull String CLOSE_SESSION_FACTORY_FAILED(final @NonNull Exception exception) {
			return CLOSE_SESSION_FACTORY_FAILED.getMessage().safeFormat(exception.getMessage())
				.traceNonNull(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.closeSessionFailed}.
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.error.closeSessionFailed}
		 */
		public static @NonNull String CLOSE_SESSION_FAILED(final @NonNull Exception exception) {
			return CLOSE_SESSION_FAILED.getMessage().safeFormat(exception.getMessage()).traceNonNull(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.commitFailed}.
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.error.commitFailed}
		 */
		public static @NonNull String COMMIT_FAILED(final @NonNull Exception exception) {
			return COMMIT_FAILED.getMessage().safeFormat(exception.getMessage()).traceNonNull(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.invalidHibernateConfig}.
		 * @return value of key {@code utils.error.invalidHibernateConfig}
		 */
		public static @NonNull String INVALID_HIBERNATE_CONFIG() {
			return INVALID_HIBERNATE_CONFIG.getMessage().traceNonNull(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.missingHibernateConfig}.
		 * @return value of key {@code utils.error.missingHibernateConfig}
		 */
		public static @NonNull String MISSING_HIBERNATE_CONFIG() {
			return MISSING_HIBERNATE_CONFIG.getMessage().traceNonNull(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.openSessionFailed}.
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.error.openSessionFailed}
		 */
		public static @NonNull String OPEN_SESSION_FAILED(final @NonNull Exception exception) {
			return OPEN_SESSION_FAILED.getMessage().safeFormat(exception.getMessage()).traceNonNull(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.transactionsHandledInternally}.
		 * @param clazz related class
		 * @return value of key {@code utils.error.transactionsHandledInternally}
		 */
		public static @NonNull String TRANSACTIONS_HANDLED_INTERNALLY(final @NonNull Class<?> clazz) {
			return TRANSACTIONS_HANDLED_INTERNALLY.getMessage().safeFormat(clazz.getSimpleName())
				.traceNonNull(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.rollbackFailed}.
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.error.rollbackFailed}
		 */
		public static @NonNull String ROLLBACK_FAILED(final @NonNull Exception exception) {
			return ROLLBACK_FAILED.getMessage().safeFormat(exception.getMessage()).traceNonNull(Error.class);
		}
	}


	/** Class storing i18n property constants for package {@code utils}. */
	public static class UtilsI18nProperty extends I18nProperty {

		/**
		 * Creates an object of type {@code I18nProperty}.
		 * @param propertyName name of I18n property
		 */
		public UtilsI18nProperty(@NonNull final String propertyName) {
			super("utils." + propertyName); //NON-NLS
		}
	}
}
