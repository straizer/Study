package semester5.pwjj.dao;

import lombok.experimental.ExtensionMethod;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import semester5.pwjj.utils.Traceable;
import semester5.pwjj.utils.extensions.ArrayUtils;
import semester5.pwjj.utils.extensions.StreamUtils;
import semester5.pwjj.utils.extensions.StringUtils;
import semester5.pwjj.utils.extensions.TraceableUtils;
import semester5.pwjj.utils.i18n.I18nProperty;
import semester5.pwjj.utils.i18n.MessageProvider;

import java.util.Locale;

/**
 * Class containing i18n constants and methods for retrieving messages
 * for package {@code dao} in the current {@link Locale}.
 * @implNote If different translation is required without changing default {@link Locale},
 * use {@link MessageProvider#get(I18nProperty, Locale)}
 */
@SuppressWarnings({"PublicInnerClass", "StaticMethodOnlyUsedInOneClass"})
@ExtensionMethod({StringUtils.class, TraceableUtils.class, ArrayUtils.class, StreamUtils.class})
public enum Messages {
	;

	/** I18n keys and value retrievers for {@code dao.error} messages. */
	public enum Error {
		;

		/** I18n key with value {@code dao.error.createEntityManagerFailed}. */
		public static final @NonNull I18nProperty CREATE_ENTITY_MANAGER_FAILED =
			new DAOI18nProperty("error.createEntityManagerFailed");

		/** I18n key with value {@code dao.error.entityAlreadyExists}. */
		public static final @NonNull I18nProperty ENTITY_ALREADY_EXISTS =
			new DAOI18nProperty("error.entityAlreadyExists");

		/** I18n key with value {@code dao.error.notAnEntityType}. */
		public static final @NonNull I18nProperty NOT_AN_ENTITY_TYPE =
			new DAOI18nProperty("error.notAnEntityType");

		/** I18n key with value {@code dao.error.notAnEntityTypeOrRemoved}. */
		public static final @NonNull I18nProperty NOT_AN_ENTITY_TYPE_OR_REMOVED =
			new DAOI18nProperty("error.notAnEntityTypeOrRemoved");

		/** I18n key with value {@code dao.error.unexpectedType}. */
		public static final @NonNull I18nProperty UNEXPECTED_TYPE =
			new DAOI18nProperty("error.unexpectedType");

		/**
		 * I18n value retriever for key {@code dao.error.createEntityManagerFailed}.
		 * @param crudMethod the CRUD method that failed
		 * @return the formatted value of key {@code dao.error.createEntityManagerFailed}
		 */
		public static @NonNull String CREATE_ENTITY_MANAGER_FAILED(final @NonNull String crudMethod) {
			return CREATE_ENTITY_MANAGER_FAILED.getMessage().safeFormat(crudMethod).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code dao.error.entityAlreadyExists}.
		 * @param clazz  the related class
		 * @param entity the related entity
		 * @param <T>    the type of {@code clazz}
		 * @return the formatted value of key {@code dao.error.entityAlreadyExists}
		 */
		public static <@NonNull T extends @NonNull Traceable> @NonNull String ENTITY_ALREADY_EXISTS(
			final @NonNull Class<@NonNull T> clazz, final @NonNull T entity
		) {
			return ENTITY_ALREADY_EXISTS.getMessage().safeFormat(clazz.getSimpleName(), entity).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code dao.error.notAnEntityType}.
		 * @param clazz  the related class
		 * @param entity the related entity
		 * @param <T>    the type of {@code clazz}
		 * @return the formatted value of key {@code dao.error.notAnEntityType}
		 */
		public static <@NonNull T extends @NonNull Traceable> @NonNull String NOT_AN_ENTITY_TYPE(
			final @NonNull Class<@NonNull T> clazz, final @Nullable T entity
		) {
			return NOT_AN_ENTITY_TYPE.getMessage().safeFormat(clazz.getSimpleName(), entity).trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code dao.error.notAnEntityTypeOrRemoved}.
		 * @param clazz  the related class
		 * @param entity the related entity
		 * @param <T>    the type of {@code clazz}
		 * @return the formatted value of key {@code dao.error.notAnEntityTypeOrRemoved}
		 */
		public static <@NonNull T extends @NonNull Traceable> @NonNull String NOT_AN_ENTITY_TYPE_OR_REMOVED(
			final @NonNull Class<@NonNull T> clazz, final @NonNull T entity
		) {
			return NOT_AN_ENTITY_TYPE_OR_REMOVED.getMessage().safeFormat(clazz.getSimpleName(), entity)
				.trace(Error.class);
		}

		/**
		 * I18n value retriever for key {@code dao.error.unexpectedType}.
		 * @param unexpectedType the unexpected type
		 * @param expectedTypes  the array of expected types
		 * @return the formatted value of key {@code dao.error.unexpectedType}
		 */
		public static @NonNull String UNEXPECTED_TYPE(
			final @NonNull String unexpectedType, final @NonNull Class<?> @NonNull ... expectedTypes
		) {
			return UNEXPECTED_TYPE.getMessage()
				.safeFormat(unexpectedType, expectedTypes.map(Class::getSimpleName).joining(", "))
				.trace(Error.class);
		}
	}

	/**
	 * Utility class representing an i18n property specifically for the {@code dao} namespace.
	 * This class extends the {@link I18nProperty} class, allowing for the creation of specialized property keys by
	 * automatically prefixing them with "{@code dao.}".
	 */
	@SuppressWarnings("ClassNamePrefixedWithPackageName")
	public static class DAOI18nProperty extends I18nProperty {

		/**
		 * Constructs an instance of {@code DAOI18nProperty} with a specific i18n property name.
		 * Prepends the property name with "{@code dao.}" to create the full property key.
		 * @param propertyName the name of the i18n property, which will be prefixed with "{@code dao.}"
		 */
		@SuppressWarnings("StringConcatenation")
		public DAOI18nProperty(final @NonNull String propertyName) {
			super("dao." + propertyName); //NON-NLS
		}
	}
}
