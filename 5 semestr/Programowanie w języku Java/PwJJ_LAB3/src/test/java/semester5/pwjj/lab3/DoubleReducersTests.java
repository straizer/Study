package semester5.pwjj.lab3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.DoubleStream;

@DisplayName("Double Reducers Tests")
class DoubleReducersTests extends TestsBase {

	@DisplayName("multiplying")
	@Test
	void multiplyingTest() {
		Assertions.assertThat(DoubleStream.of(2.0, 3.0).reduce(1.0, DoubleReducers.MULTIPLYING))
			.isEqualTo(6.0);
	}

	@DisplayName("multiplying empty")
	@Test
	void multiplyingEmptyTest() {
		Assertions.assertThat(DoubleStream.of().reduce(1.0, DoubleReducers.MULTIPLYING))
			.isEqualTo(1.0);
	}
}
