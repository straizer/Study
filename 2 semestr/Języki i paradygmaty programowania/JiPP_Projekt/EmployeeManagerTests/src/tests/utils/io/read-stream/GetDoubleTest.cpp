#include "utils-test/GetInputCommon.hpp"

namespace UtilsReadStreamTest
{
	TEST_CLASS(GetDoubleTest)
	{
	public:

		TEST_METHOD(WhenEmptyStream_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = UtilsReadStreamTest::getStream();

			// When
			const std::optional<double> actualValue = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenSingleCallOneDouble_ShouldReturnDouble)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1);

			// When
			const std::optional<double> actualValue = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue.value());
		}

		TEST_METHOD(WhenDoubleCallOneDouble_ShouldReturnDoubleOnFirstAndNullOptionalOnSecond)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1);

			// When
			const std::optional<double> actualValue1 = utils::IO::readStream<double>("", defaultValidator, stream);
			const std::optional<double> actualValue2 = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue1.value());
			Assert::IsFalse(actualValue2.has_value());
		}

		TEST_METHOD(WhenSingleCallTwoDoubles_ShouldReturnFirstDouble)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1, " ", expectedValue2);

			// When
			const std::optional<double> actualValue = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue.value());
		}

		TEST_METHOD(WhenDoubleCallTwoDoublesSeparatedBySpace_ShouldReturnBothDoubles)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1, " ", expectedValue2);

			// When
			const std::optional<double> actualValue1 = utils::IO::readStream<double>("", defaultValidator, stream);
			const std::optional<double> actualValue2 = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue1.value());
			Assert::AreEqual(expectedValue2, actualValue2.value());
		}

		TEST_METHOD(WhenDoubleCallTwoDoublesSeparatedByNewLine_ShouldReturnBothDoubles)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1, "\n", expectedValue2);

			// When
			const std::optional<double> actualValue1 = utils::IO::readStream<double>("", defaultValidator, stream);
			const std::optional<double> actualValue2 = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue1.value());
			Assert::AreEqual(expectedValue2, actualValue2.value());
		}

		TEST_METHOD(WhenSingleCallOneDoubleWithLeadingWhitespaces_ShouldReturnDouble)
		{
			// Given
			std::istringstream stream = getStream(whitespaces, expectedValue1);

			// When
			const std::optional<double> actualValue = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue.value());
		}

		TEST_METHOD(WhenDoubleCallTwoDoublesWithLeadingWhitespaces_ShouldReturnBothDoubles)
		{
			// Given
			std::istringstream stream = getStream(whitespaces, expectedValue1, whitespaces, expectedValue2);

			// When
			const std::optional<double> actualValue1 = utils::IO::readStream<double>("", defaultValidator, stream);
			const std::optional<double> actualValue2 = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue1.value());
			Assert::AreEqual(expectedValue2, actualValue2.value());
		}

		TEST_METHOD(WhenSingleCallStringAndDoubleNotSeparated_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = getStream(string, expectedValue1);

			// When
			const std::optional<double> actualValue = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenSingleCallStringAndDoubleSeparatedBySpace_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = getStream(string, " ", expectedValue1);

			// When
			const std::optional<double> actualValue = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenSingleCallStringAndDoubleSeparatedByNewLine_ShouldReturnDouble)
		{
			// Given
			std::istringstream stream = getStream(string, "\n", expectedValue1);

			// When
			const std::optional<double> actualValue = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue.value());
		}

		TEST_METHOD(WhenSingleCallOneString_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = getStream(string);

			// When
			const std::optional<double> actualValue = utils::IO::readStream<double>("", defaultValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenSingleCallOneDoubleValidatorNotPassed_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1);

			// When
			const std::optional<double> actualValue = utils::IO::readStream<double>("", isPositive, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenDoubleCallTwoDoublesValidatorPassedForSecond_ShouldReturnSecond)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1, "\n", expectedValue2);

			// When
			const std::optional<double> actualValue = utils::IO::readStream<double>("", isPositive, stream);

			// Then
			Assert::AreEqual(expectedValue2, actualValue.value());
		}

	private:

		inline static const double expectedValue1 = -0.42;
		inline static const double expectedValue2 = 0.9;
		inline static const std::string string = "test";
		inline static const std::function<bool(const double)> defaultValidator = [](const double) { return true; };
		inline static const std::function<bool(const double)> isPositive = [](const double value) { return value > 0; };

		template<class... Args>
		[[nodiscard]] auto getStream(const Args&... values) -> std::istringstream
		{
			return UtilsReadStreamTest::getStream(utils::string::concatenate()(values...));
		}
	};
}
