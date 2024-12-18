package semester5.pwjj.entities.shapes;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.BeforeAll;
import semester5.pwjj.entities.EntitiesTestsBase;
import semester5.pwjj.utils.i18n.I18nProperty;

/** Abstract base class for {@code entities.shapes} test setups and utilities. */
@SuppressWarnings("AbstractClassWithOnlyOneDirectInheritor")
public abstract class EntitiesShapesTestsBase extends EntitiesTestsBase {

	protected static final @NonNull I18nProperty ERROR_SIDES_ARE_NULL = Messages.Error.SIDES_ARE_NULL;
	protected static final @NonNull I18nProperty ERROR_SIDES_NOT_POSITIVE = Messages.Error.SIDES_NOT_POSITIVE;
	protected static final @NonNull I18nProperty ERROR_TRIANGLE_RULE = Messages.Error.TRIANGLE_RULE;
	protected static final @NonNull I18nProperty TO_STRING_SHAPE = Messages.ToString.SHAPE;

	/** Method executed once before all tests in each extending class. */
	@BeforeAll
	static void entitiesShapesBeforeAll() throws IllegalAccessException {
		populateMessageProviderMock(EntitiesShapesTestsBase.class);
	}
}
