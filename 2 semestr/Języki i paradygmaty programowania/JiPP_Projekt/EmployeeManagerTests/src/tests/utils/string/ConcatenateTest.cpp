#include "Common.hpp"

#include "utils/StringUtils.hpp"

class NoInsertionClass final {};
class InsertionClass final {};
std::ostream& operator<<(std::ostream& stream, const InsertionClass& instance)
{
	return stream << typeid(instance).name();
}

namespace UtilsStringTest
{
	TEST_CLASS(ConcatenateTest)
	{
	public:

		TEST_METHOD(WhenParametersArePassed_ShouldReturnConcatenatedString)
		{
			// Given
			const std::string expectedValue = "5A3.14foo";

			// When
			const std::string actualValue = utils::string::concatenate()(5, 'A', 3.14f, "foo");

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenRvalueSeparatorAndParametersArePassed_ShouldReturnConcatenatedString)
		{
			// Given
			const std::string expectedValue = "5 A 3.14 foo";

			// When
			const std::string actualValue = utils::string::concatenate(" ")(5, 'A', 3.14f, "foo");

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenLvalueSeparatorAndParametersArePassed_ShouldReturnConcatenatedString)
		{
			// Given
			const std::string expectedValue = "5 A 3.14 foo";
			const std::string separator = " ";

			// When
			const std::string actualValue = utils::string::concatenate(separator)(5, 'A', 3.14f, "foo");

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenNoParameterIsPassed_ShouldReturnEmptyString)
		{
			// Given
			const std::string expectedValue = "5";

			// When
			const std::string actualValue = utils::string::concatenate()(5);

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenClassWithInsertOperatorIsPassed_ShouldReturnClassRepresentation)
		{
			// Given
			InsertionClass instance;
			const std::string expectedValue = typeid(instance).name();

			// When
			const std::string actualValue = utils::string::concatenate()(instance);

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}
	};
}
