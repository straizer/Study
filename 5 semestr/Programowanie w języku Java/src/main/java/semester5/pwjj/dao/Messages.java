package semester5.pwjj.dao;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.Representative;
import semester5.pwjj.utils.StringUtils;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code dao} in the current locale.
 * @implNote If different translation is required without changing default locale,
 * use {@link MessageProvider#get(I18nProperty i18nProperty, Locale locale)}
 */
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code dao.error} messages. */
	public enum Error {
		;

		/** I18n key with value {@code dao.error.entityAlreadyExists}. */
		public static final @NonNull I18nProperty ENTITY_ALREADY_EXISTS =
			new DAOI18nProperty("error.entityAlreadyExists");

		/** I18n key with value {@code dao.error.notAnEntity}. */
		public static final @NonNull I18nProperty NOT_AN_ENTITY_TYPE =
			new DAOI18nProperty("error.notAnEntityType");

		/** I18n key with value {@code dao.error.notAnEntityOrRemoved}. */
		public static final @NonNull I18nProperty NOT_AN_ENTITY_TYPE_OR_REMOVED =
			new DAOI18nProperty("error.notAnEntityTypeOrRemoved");

		/** I18n key with value {@code dao.error.openSessionFailed}. */
		public static final @NonNull I18nProperty OPEN_SESSION_FAILED =
			new DAOI18nProperty("error.openSessionFailed");

		/**
		 * I18n value retriever for key {@code dao.error.entityAlreadyExists}.
		 * @param clazz  related class
		 * @param entity related entity
		 * @param <T>    type of {@code clazz}
		 * @return value of key {@code dao.error.entityAlreadyExists}
		 */
		public static <T extends Representative> @NonNull String ENTITY_ALREADY_EXISTS(
			final @NonNull Class<T> clazz, final @NonNull T entity) {
			return Representative.traceNonNull(
				StringUtils.format(MessageProvider.get(ENTITY_ALREADY_EXISTS), clazz.getSimpleName(), entity),
				Error.class);
		}

		/**
		 * I18n value retriever for key {@code dao.error.notAnEntity}.
		 * @param clazz  related class
		 * @param entity related entity
		 * @param <T>    type of {@code clazz}
		 * @return value of key {@code dao.error.notAnEntity}
		 */
		public static <T extends Representative> @NonNull String NOT_AN_ENTITY_TYPE(
			final @NonNull Class<T> clazz, final @Nullable T entity) {
			return Representative.traceNonNull(
				StringUtils.format(MessageProvider.get(NOT_AN_ENTITY_TYPE), clazz.getSimpleName(), entity),
				Error.class);
		}

		/**
		 * I18n value retriever for key {@code dao.error.notAnEntityOrRemoved}.
		 * @param clazz  related class
		 * @param entity related entity
		 * @param <T>    type of {@code clazz}
		 * @return value of key {@code dao.error.notAnEntityOrRemoved}
		 */
		public static <T extends Representative> @NonNull String NOT_AN_ENTITY_TYPE_OR_REMOVED(
			final @NonNull Class<T> clazz, final @NonNull T entity) {
			return Representative.traceNonNull(
				StringUtils.format(
					MessageProvider.get(NOT_AN_ENTITY_TYPE_OR_REMOVED), clazz.getSimpleName(), entity), Error.class);
		}

		/**
		 * I18n value retriever for key {@code dao.error.openSessionFailed}.
		 * @param crudMethod CRUD method that failed
		 * @return value of key {@code dao.error.openSessionFailed}
		 */
		public static @NonNull String OPEN_SESSION_FAILED(final @NonNull String crudMethod) {
			return Representative.traceNonNull(
				StringUtils.format(MessageProvider.get(OPEN_SESSION_FAILED), crudMethod), Error.class);
		}
	}


	/** Class storing i18n property constants for package {@code dao}. */
	public static class DAOI18nProperty extends I18nProperty {

		/**
		 * Creates an object of type {@code I18nProperty}.
		 * @param propertyName name of I18n property
		 */
		public DAOI18nProperty(@NonNull final String propertyName) {
			super("dao." + propertyName); //NON-NLS
		}
	}
}
