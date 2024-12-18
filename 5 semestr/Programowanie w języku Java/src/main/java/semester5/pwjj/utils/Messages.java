package semester5.pwjj.utils;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.extensions.TraceableUtils;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code utils} in the current {@link Locale}.
 * @implNote If different translation is required without changing default {@link Locale},
 * use {@link MessageProvider#get(I18nProperty, Locale)}
 */
@SuppressWarnings({"PublicInnerClass", "StaticMethodOnlyUsedInOneClass"})
@ExtensionMethod({StringUtils.class, TraceableUtils.class})
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code utils.error} messages. */
	public enum Error {
		;

		/** I18n key with value {@code utils.error.cannotAddShutdownHook}. */
		public static final @NonNull I18nProperty CANNOT_ADD_SHUTDOWN_HOOK =
			new UtilsI18nProperty("error.cannotAddShutdownHook");

		/** I18n key with value {@code utils.error.closeEntityManagerFactoryFailed}. */
		public static final @NonNull I18nProperty CLOSE_ENTITY_MANAGER_FACTORY_FAILED =
			new UtilsI18nProperty("error.closeEntityManagerFactoryFailed");

		/** I18n key with value {@code utils.error.closeEntityManagerFailed}. */
		public static final @NonNull I18nProperty CLOSE_ENTITY_MANAGER_FAILED =
			new UtilsI18nProperty("error.closeEntityManagerFailed");

		/** I18n key with value {@code utils.error.commitFailed}. */
		public static final @NonNull I18nProperty COMMIT_FAILED =
			new UtilsI18nProperty("error.commitFailed");

		/** I18n key with value {@code utils.error.invalidHibernateConfig}. */
		public static final @NonNull I18nProperty INVALID_HIBERNATE_CONFIG =
			new UtilsI18nProperty("error.invalidHibernateConfig");

		/** I18n key with value {@code utils.error.missingHibernateConfig}. */
		public static final @NonNull I18nProperty MISSING_HIBERNATE_CONFIG =
			new UtilsI18nProperty("error.missingHibernateConfig");

		/** I18n key with value {@code utils.error.openEntityManagerFailed}. */
		public static final @NonNull I18nProperty OPEN_ENTITY_MANAGER_FAILED =
			new UtilsI18nProperty("error.openEntityManagerFailed");

		/** I18n key with value {@code utils.error.transactionsHandledInternally}. */
		public static final @NonNull I18nProperty TRANSACTIONS_HANDLED_INTERNALLY =
			new UtilsI18nProperty("error.transactionsHandledInternally");

		/** I18n key with value {@code utils.error.rollbackFailed}. */
		public static final @NonNull I18nProperty ROLLBACK_FAILED =
			new UtilsI18nProperty("error.rollbackFailed");

		/**
		 * I18n value retriever for key {@code utils.error.cannotAddShutdownHook}.
		 * @param methodName the name of the method that failed to be added as shutdown hook
		 * @return the formatted value of key {@code utils.error.cannotAddShutdownHook}
		 */
		public static @NonNull String CANNOT_ADD_SHUTDOWN_HOOK(final @NonNull String methodName) {
			return CANNOT_ADD_SHUTDOWN_HOOK.getMessage().safeFormat(methodName).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.closeEntityManagerFactoryFailed}.
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.error.closeEntityManagerFactoryFailed}
		 */
		public static @NonNull String CLOSE_ENTITY_MANAGER_FACTORY_FAILED(final @NonNull Exception exception) {
			return CLOSE_ENTITY_MANAGER_FACTORY_FAILED.getMessage().safeFormat(exception.getMessage())
				.trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.closeEntityManagerFailed}.
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.error.closeEntityManagerFailed}
		 */
		public static @NonNull String CLOSE_ENTITY_MANAGER_FAILED(final @NonNull Exception exception) {
			return CLOSE_ENTITY_MANAGER_FAILED.getMessage().safeFormat(exception.getMessage()).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.commitFailed}.
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.error.commitFailed}
		 */
		public static @NonNull String COMMIT_FAILED(final @NonNull Exception exception) {
			return COMMIT_FAILED.getMessage().safeFormat(exception.getMessage()).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.invalidHibernateConfig}.
		 * @return the value of key {@code utils.error.invalidHibernateConfig}
		 */
		public static @NonNull String INVALID_HIBERNATE_CONFIG() {
			return INVALID_HIBERNATE_CONFIG.getMessage().trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.missingHibernateConfig}.
		 * @return the value of key {@code utils.error.missingHibernateConfig}
		 */
		public static @NonNull String MISSING_HIBERNATE_CONFIG() {
			return MISSING_HIBERNATE_CONFIG.getMessage().trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.openEntityManagerFailed}.
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.error.openEntityManagerFailed}
		 */
		public static @NonNull String OPEN_ENTITY_MANAGER_FAILED(final @NonNull Exception exception) {
			return OPEN_ENTITY_MANAGER_FAILED.getMessage().safeFormat(exception.getMessage()).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.transactionsHandledInternally}.
		 * @param clazz the related class
		 * @return the formatted value of key {@code utils.error.transactionsHandledInternally}
		 */
		public static @NonNull String TRANSACTIONS_HANDLED_INTERNALLY(final @NonNull Class<?> clazz) {
			return TRANSACTIONS_HANDLED_INTERNALLY.getMessage().safeFormat(clazz.getSimpleName()).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.rollbackFailed}.
		 * @param exception the exception that was thrown
		 * @return the formatted value of key {@code utils.error.rollbackFailed}
		 */
		public static @NonNull String ROLLBACK_FAILED(final @NonNull Exception exception) {
			return ROLLBACK_FAILED.getMessage().safeFormat(exception.getMessage()).trace(Error.class);
		}
	}

	/**
	 * Utility class representing an i18n property specifically for the {@code utils} namespace.
	 * This class extends the {@link I18nProperty} class, allowing for the creation of specialized property keys by
	 * automatically prefixing them with "{@code utils.}".
	 */
	@SuppressWarnings("ClassNamePrefixedWithPackageName")
	public static class UtilsI18nProperty extends I18nProperty {

		/**
		 * Constructs an instance of {@code UtilsI18nProperty} with a specific i18n property name.
		 * Prepends the property name with "{@code utils.}" to create the full property key.
		 * @param propertyName the name of the i18n property, which will be prefixed with "{@code utils.}"
		 */
		@SuppressWarnings("StringConcatenation")
		public UtilsI18nProperty(final @NonNull String propertyName) {
			super("utils." + propertyName); //NON-NLS
		}
	}
}
