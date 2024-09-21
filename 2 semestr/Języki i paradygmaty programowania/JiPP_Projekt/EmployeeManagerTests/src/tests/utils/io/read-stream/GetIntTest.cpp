#include "utils-test/GetInputCommon.hpp"

namespace UtilsReadStreamTest
{
	TEST_CLASS(GetIntTest)
	{
	public:

		TEST_METHOD(WhenEmptyStream_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = UtilsReadStreamTest::getStream();

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenSingleCallOneInt_ShouldReturnInt)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue.value());
		}

		TEST_METHOD(WhenDoubleCallOneInt_ShouldReturnIntOnFirstAndNullOptionalOnSecond)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1);

			// When
			const std::optional<int> actualValue1 = utils::IO::readStream<int>("", defaultValidator, stream);
			const std::optional<int> actualValue2 = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue1.value());
			Assert::IsFalse(actualValue2.has_value());
		}

		TEST_METHOD(WhenSingleCallTwoInts_ShouldReturnFirstInt)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1, " ", expectedValue2);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue.value());
		}

		TEST_METHOD(WhenDoubleCallTwoIntsSeparatedBySpace_ShouldReturnBothInts)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1, " ", expectedValue2);

			// When
			const std::optional<int> actualValue1 = utils::IO::readStream<int>("", defaultValidator, stream);
			const std::optional<int> actualValue2 = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue1.value());
			Assert::AreEqual(expectedValue2, actualValue2.value());
		}

		TEST_METHOD(WhenDoubleCallTwoIntsSeparatedByNewLine_ShouldReturnBothInts)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1, "\n", expectedValue2);

			// When
			const std::optional<int> actualValue1 = utils::IO::readStream<int>("", defaultValidator, stream);
			const std::optional<int> actualValue2 = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue1.value());
			Assert::AreEqual(expectedValue2, actualValue2.value());
		}

		TEST_METHOD(WhenSingleCallOneIntWithLeadingWhitespaces_ShouldReturnInt)
		{
			// Given
			std::istringstream stream = getStream(whitespaces, expectedValue1);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue.value());
		}

		TEST_METHOD(WhenDoubleCallTwoIntsWithLeadingWhitespaces_ShouldReturnBothInts)
		{
			// Given
			std::istringstream stream = getStream(whitespaces, expectedValue1, whitespaces, expectedValue2);

			// When
			const std::optional<int> actualValue1 = utils::IO::readStream<int>("", defaultValidator, stream);
			const std::optional<int> actualValue2 = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue1.value());
			Assert::AreEqual(expectedValue2, actualValue2.value());
		}

		TEST_METHOD(WhenSingleCallStringAndIntNotSeparated_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = getStream(string, expectedValue1);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenSingleCallStringAndIntSeparatedBySpace_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = getStream(string, " ", expectedValue1);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenSingleCallStringAndIntSeparatedByNewLine_ShouldReturnInt)
		{
			// Given
			std::istringstream stream = getStream(string, "\n", expectedValue1);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::AreEqual(expectedValue1, actualValue.value());
		}

		TEST_METHOD(WhenSingleCallOneString_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = getStream(string);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenSingleCallOneIntValidatorNotPassed_ShouldReturnNullOptional)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", isPositive, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
		}

		TEST_METHOD(WhenDoubleCallTwoIntsValidatorPassedForSecond_ShouldReturnSecond)
		{
			// Given
			std::istringstream stream = getStream(expectedValue1, " \n", expectedValue2);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", isPositive, stream);

			// Then
			Assert::AreEqual(expectedValue2, actualValue.value());
		}

	private:

		inline static const int expectedValue1 = -42;
		inline static const int expectedValue2 = 90;
		inline static const std::string string = "test";
		inline static const std::function<bool(const int)> defaultValidator = [](const int) { return true; };
		inline static const std::function<bool(const int)> isPositive = [](const int value) { return value > 0; };

		template<class... Args>
		[[nodiscard]] auto getStream(const Args&... values) -> std::istringstream
		{
			return UtilsReadStreamTest::getStream(utils::string::concatenate()(values...));
		}
	};
}
