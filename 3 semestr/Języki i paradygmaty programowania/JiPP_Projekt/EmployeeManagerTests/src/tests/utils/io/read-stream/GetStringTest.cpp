#include "utils-test/GetInputCommon.hpp"

namespace UtilsReadStreamTest
{
	TEST_CLASS(GetStringTest)
	{
	public:

		TEST_METHOD(WhenEmptyStream_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = UtilsReadStreamTest::getStream();

			// When
			const std::optional<std::string> actualValue = utils::IO::readStream<std::string>("", defaultValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenSingleCallOneString_ShouldReturnString)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1);

			// When
			const std::optional<std::string> actualValue = utils::IO::readStream<std::string>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue.value());
		}

		TEST_METHOD(WhenDoubleCallOneString_ShouldReturnStringOnFirstAndNullOptionalOnSecond)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1);

			// When
			const std::optional<std::string> actualValue1 = utils::IO::readStream<std::string>("", defaultValidator, stream);
			const std::optional<std::string> actualValue2 = utils::IO::readStream<std::string>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue1.value());
			Assert::IsFalse(actualValue2.has_value());
		}

		TEST_METHOD(WhenDoubleCallTwoStringsSeparatedByNewLine_ShouldReturnBothStrings)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1, "\n", expectedValue2);

			// When
			const std::optional<std::string> actualValue1 = utils::IO::readStream<std::string>("", defaultValidator, stream);
			const std::optional<std::string> actualValue2 = utils::IO::readStream<std::string>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue1.value());
			Assert::AreEqual(expectedValue2, actualValue2.value());
		}

		TEST_METHOD(WhenSingleCallOneStringWithLeadingWhitespaces_ShouldReturnString)
		{
			// Given
			std::istringstream stream = getStream(whitespaces, expectedValue1);

			// When
			const std::optional<std::string> actualValue = utils::IO::readStream<std::string>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue.value());
		}

		TEST_METHOD(WhenSingleCallOneStringValidatorNotPassed_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1);

			// When
			const std::optional<std::string> actualValue = utils::IO::readStream<std::string>("", containsAnd, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenDoubleCallTwoStringsValidatorPassedForSecond_ShouldReturnSecond)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1, "\n", expectedValue2);

			// When
			const std::optional<std::string> actualValue = utils::IO::readStream<std::string>("", containsAnd, stream);

			// Then
			Assert::AreEqual(expectedValue2, actualValue.value());
		}

	private:

		inline static const std::string expectedValue1 = "first line";
		inline static const std::string expectedValue2 = "and second";
		inline static const std::function<bool(const std::string&)> defaultValidator = [](const std::string&) { return true; };
		inline static const std::function<bool(const std::string&)> containsAnd = [](const std::string& value) { return value.contains("and"); };

		template<class... Args>
		[[nodiscard]] auto getStream(const Args&... values) -> std::istringstream
		{
			return UtilsReadStreamTest::getStream(utils::string::concatenate()(values...));
		}
	};
}
