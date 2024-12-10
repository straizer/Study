package semester5.pwjj.utils;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.Representative;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code utils} in the current locale.
 * @implNote If different translation is required without changing default locale,
 * use {@link MessageProvider#get(I18nProperty i18nProperty, Locale locale)}
 */
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

		/** I18n key with value {@code utils.error.formatting}. */
		public static final @NonNull I18nProperty FORMATTING =
			new UtilsI18nProperty("error.formatting");

		/** I18n key with value {@code utils.error.invalidHibernateConfig}. */
		public static final @NonNull I18nProperty INVALID_HIBERNATE_CONFIG =
			new UtilsI18nProperty("error.invalidHibernateConfig");

		/** I18n key with value {@code utils.error.missingHibernateConfig}. */
		public static final @NonNull I18nProperty MISSING_HIBERNATE_CONFIG =
			new UtilsI18nProperty("error.missingHibernateConfig");

		/** I18n key with value {@code utils.error.openSessionFailed}. */
		public static final @NonNull I18nProperty OPEN_SESSION_FAILED =
			new UtilsI18nProperty("error.openSessionFailed");

		/** I18n key with value {@code utils.error.rollbackFailed}. */
		public static final @NonNull I18nProperty ROLLBACK_FAILED =
			new UtilsI18nProperty("error.rollbackFailed");

		/**
		 * I18n value retriever for key {@code utils.error.cannotAddShutdownHook}.
		 * @param methodName name of the method that failed to be added to shutdown hook
		 * @return value of key {@code utils.error.cannotAddShutdownHook}
		 */
		public static @NonNull String CANNOT_ADD_SHUTDOWN_HOOK(final @NonNull String methodName) {
			return Representative.traceNonNull(
				StringUtils.format(MessageProvider.get(CANNOT_ADD_SHUTDOWN_HOOK), methodName), Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.closeSessionFactoryFailed}.
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.error.closeSessionFactoryFailed}
		 */
		public static @NonNull String CLOSE_SESSION_FACTORY_FAILED(final @NonNull Exception exception) {
			return Representative.traceNonNull(
				StringUtils.format(MessageProvider.get(CLOSE_SESSION_FACTORY_FAILED), exception.getMessage()),
				Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.closeSessionFailed}.
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.error.closeSessionFailed}
		 */
		public static @NonNull String CLOSE_SESSION_FAILED(final @NonNull Exception exception) {
			return Representative.traceNonNull(
				StringUtils.format(MessageProvider.get(CLOSE_SESSION_FAILED), exception.getMessage()), Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.commitFailed}.
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.error.commitFailed}
		 */
		public static @NonNull String COMMIT_FAILED(final @NonNull Exception exception) {
			return Representative.traceNonNull(
				StringUtils.format(MessageProvider.get(COMMIT_FAILED), exception.getMessage()), Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.formatting}.
		 * @param template  template for which formatting failed
		 * @param args      args for which formatting failed
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.error.formatting}
		 */
		public static @NonNull String FORMATTING(
			final @NonNull String template, final @Nullable Object @Nullable [] args,
			final @NonNull IllegalFormatException exception) {
			return Representative.traceNonNull(
				StringUtils.format(
					MessageProvider.get(FORMATTING), template, Arrays.toString(args), exception.getMessage()),
				Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.invalidHibernateConfig}.
		 * @return value of key {@code utils.error.invalidHibernateConfig}
		 */
		public static @NonNull String INVALID_HIBERNATE_CONFIG() {
			return Representative.traceNonNull(MessageProvider.get(INVALID_HIBERNATE_CONFIG), Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.missingHibernateConfig}.
		 * @return value of key {@code utils.error.missingHibernateConfig}
		 */
		public static @NonNull String MISSING_HIBERNATE_CONFIG() {
			return Representative.traceNonNull(MessageProvider.get(MISSING_HIBERNATE_CONFIG), Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.openSessionFailed}.
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.error.openSessionFailed}
		 */
		public static @NonNull String OPEN_SESSION_FAILED(final @NonNull Exception exception) {
			return Representative.traceNonNull(
				StringUtils.format(MessageProvider.get(OPEN_SESSION_FAILED), exception.getMessage()), Error.class);
		}

		/**
		 * I18n value retriever for key {@code utils.error.rollbackFailed}.
		 * @param exception exception that was thrown
		 * @return value of key {@code utils.error.rollbackFailed}
		 */
		public static @NonNull String ROLLBACK_FAILED(final @NonNull Exception exception) {
			return Representative.traceNonNull(
				StringUtils.format(MessageProvider.get(ROLLBACK_FAILED), exception.getMessage()), Error.class);
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
