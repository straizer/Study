package semester5.pwjj.lab3;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.AfterEach;

import java.util.Locale;
import java.util.stream.Stream;

/**
 * Base class for all tests.
 */
public abstract class TestsBase {

	@NonNull
	protected static Stream<Locale> localeSource() {
		return Stream.of(Locale.of("pl"), Locale.ENGLISH);
	}

	@AfterEach
	void teardown() {
		Locale.setDefault(Locale.ROOT);
	}
}
