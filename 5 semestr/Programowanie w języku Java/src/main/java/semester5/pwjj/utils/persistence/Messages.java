package semester5.pwjj.utils.persistence;

import lombok.experimental.ExtensionMethod;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.extensions.TraceableUtils;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code utils.persistence} in the current {@link Locale}.
 * @implNote If different translation is required without changing default {@link Locale},
 * use {@link MessageProvider#get(I18nProperty, Locale)}
 */
@SuppressWarnings({"PublicInnerClass", "StaticMethodOnlyUsedInOneClass", "ClassWithTooManyFields"})
@ExtensionMethod({StringUtils.class, TraceableUtils.class})
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code utils.persistence.error} messages. */
	public enum Error {
		;

		/** I18n key with value {@code utils.persistence.error.cannotAddShutdownHook}. */
		public static final I18nProperty CANNOT_ADD_SHUTDOWN_HOOK =
			new UtilsI18nProperty("error.cannotAddShutdownHook");

		/** I18n key with value {@code utils.persistence.error.closeEntityManagerFactoryFailed}. */
		public static final I18nProperty CLOSE_ENTITY_MANAGER_FACTORY_FAILED =
			new UtilsI18nProperty("error.closeEntityManagerFactoryFailed");

		/** I18n key with value {@code utils.persistence.error.closeEntityManagerFailed}. */
		public static final I18nProperty CLOSE_ENTITY_MANAGER_FAILED =
			new UtilsI18nProperty("error.closeEntityManagerFailed");

		/** I18n key with value {@code utils.persistence.error.commitFailed}. */
		public static final I18nProperty COMMIT_FAILED =
			new UtilsI18nProperty("error.commitFailed");

		/** I18n key with value {@code utils.persistence.error.invalidHibernateConfig}. */
		public static final I18nProperty INVALID_HIBERNATE_CONFIG =
			new UtilsI18nProperty("error.invalidHibernateConfig");

		/** I18n key with value {@code utils.persistence.error.missingDatabasePassword}. */
		public static final I18nProperty MISSING_DATABASE_PASSWORD =
			new UtilsI18nProperty("error.missingDatabasePassword");

		/** I18n key with value {@code utils.persistence.error.missingDatabaseUsername}. */
		public static final I18nProperty MISSING_DATABASE_USERNAME =
			new UtilsI18nProperty("error.missingDatabaseUsername");

		/** I18n key with value {@code utils.persistence.error.missingHibernateConfig}. */
		public static final I18nProperty MISSING_HIBERNATE_CONFIG =
			new UtilsI18nProperty("error.missingHibernateConfig");

		/** I18n key with value {@code utils.persistence.error.openEntityManagerFailed}. */
		public static final I18nProperty OPEN_ENTITY_MANAGER_FAILED =
			new UtilsI18nProperty("error.openEntityManagerFailed");

		/** I18n key with value {@code utils.persistence.error.transactionsHandledInternally}. */
		public static final I18nProperty TRANSACTIONS_HANDLED_INTERNALLY =
			new UtilsI18nProperty("error.transactionsHandledInternally");

		/** I18n key with value {@code utils.persistence.error.rollbackFailed}. */
		public static final I18nProperty ROLLBACK_FAILED =
			new UtilsI18nProperty("error.rollbackFailed");

		/**
		 * I18n value retriever for key {@code utils.persistence.error.cannotAddShutdownHook}.
		 * @param methodName the name of the method that failed to be added as shutdown hook
		 * @return the formatted value of key {@code utils.persistence.error.cannotAddShutdownHook}
		 */
		public static String CANNOT_ADD_SHUTDOWN_HOOK(final String methodName) {
			return CANNOT_ADD_SHUTDOWN_HOOK.getMessage().safeFormat(methodName).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.persistence.error.closeEntityManagerFactoryFailed}.
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.persistence.error.closeEntityManagerFactoryFailed}
		 */
		public static String CLOSE_ENTITY_MANAGER_FACTORY_FAILED(final Exception exception) {
			return CLOSE_ENTITY_MANAGER_FACTORY_FAILED.getMessage().safeFormat(exception.getMessage())
				.trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.persistence.error.closeEntityManagerFailed}.
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.persistence.error.closeEntityManagerFailed}
		 */
		public static String CLOSE_ENTITY_MANAGER_FAILED(final Exception exception) {
			return CLOSE_ENTITY_MANAGER_FAILED.getMessage().safeFormat(exception.getMessage()).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.persistence.error.commitFailed}.
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.persistence.error.commitFailed}
		 */
		public static String COMMIT_FAILED(final Exception exception) {
			return COMMIT_FAILED.getMessage().safeFormat(exception.getMessage()).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.persistence.error.invalidHibernateConfig}.
		 * @return the value of key {@code utils.persistence.error.invalidHibernateConfig}
		 */
		public static String INVALID_HIBERNATE_CONFIG() {
			return INVALID_HIBERNATE_CONFIG.getMessage().trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.persistence.error.missingDatabasePassword}.
		 * @return the value of key {@code utils.persistence.error.missingDatabasePassword}
		 */
		public static String MISSING_DATABASE_PASSWORD() {
			return MISSING_DATABASE_PASSWORD.getMessage().trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.persistence.error.missingDatabaseUsername}.
		 * @return the value of key {@code utils.persistence.error.missingDatabaseUsername}
		 */
		public static String MISSING_DATABASE_USERNAME() {
			return MISSING_DATABASE_USERNAME.getMessage().trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.persistence.error.missingHibernateConfig}.
		 * @return the value of key {@code utils.persistence.error.missingHibernateConfig}
		 */
		public static String MISSING_HIBERNATE_CONFIG() {
			return MISSING_HIBERNATE_CONFIG.getMessage().trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.persistence.error.openEntityManagerFailed}.
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.persistence.error.openEntityManagerFailed}
		 */
		public static String OPEN_ENTITY_MANAGER_FAILED(final Exception exception) {
			return OPEN_ENTITY_MANAGER_FAILED.getMessage().safeFormat(exception.getMessage()).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.persistence.error.transactionsHandledInternally}.
		 * @param clazz the related class
		 * @return the formatted value of key {@code utils.persistence.error.transactionsHandledInternally}
		 */
		public static String TRANSACTIONS_HANDLED_INTERNALLY(final Class<?> clazz) {
			return TRANSACTIONS_HANDLED_INTERNALLY.getMessage().safeFormat(clazz.getSimpleName()).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.persistence.error.rollbackFailed}.
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.persistence.error.rollbackFailed}
		 */
		public static String ROLLBACK_FAILED(final Exception exception) {
			return ROLLBACK_FAILED.getMessage().safeFormat(exception.getMessage()).trace(Error.class);
		}
	}

	/**
	 * Utility class representing an i18n property specifically for the {@code utils.persistence} namespace.
	 * This class extends the {@link I18nProperty} class, allowing for the creation of specialized property keys by
	 * automatically prefixing them with "{@code utils.persistence.}".
	 */
	@SuppressWarnings("ClassNamePrefixedWithPackageName")
	public static class UtilsI18nProperty extends I18nProperty {

		/**
		 * Constructs an instance of {@code UtilsI18nProperty} with a specific i18n property name.
		 * Prepends the property name with "{@code utils.persistence.}" to create the full property key.
		 * @param propertyName the name of the i18n property, which will be prefixed with "{@code utils.persistence.}"
		 */
		@SuppressWarnings("StringConcatenation")
		public UtilsI18nProperty(final String propertyName) {
			super("utils.persistence." + propertyName); //NON-NLS
		}
	}
}
