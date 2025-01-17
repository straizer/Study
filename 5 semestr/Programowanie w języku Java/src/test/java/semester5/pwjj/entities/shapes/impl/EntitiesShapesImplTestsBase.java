package semester5.pwjj.entities.shapes.impl;

import org.junit.jupiter.api.BeforeAll;
import semester5.pwjj.entities.shapes.EntitiesShapesTestsBase;
import semester5.pwjj.utils.i18n.I18nProperty;

/** Abstract base class for {@code entities.shapes.impl} test setups and utilities. */
public abstract class EntitiesShapesImplTestsBase extends EntitiesShapesTestsBase {

	protected static final I18nProperty NAME_RECTANGLE = Messages.Name.RECTANGLE;
	protected static final I18nProperty NAME_TRIANGLE = Messages.Name.TRIANGLE;

	/** Method executed once before all tests in each extending class. */
	@BeforeAll
	static void entitiesShapesImplBeforeAll() throws IllegalAccessException {
		populateMessageProviderMock(EntitiesShapesImplTestsBase.class);
	}
}
