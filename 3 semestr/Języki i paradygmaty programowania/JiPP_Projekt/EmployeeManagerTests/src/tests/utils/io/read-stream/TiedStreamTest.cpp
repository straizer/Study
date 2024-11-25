#include "utils-test/GetInputCommon.hpp"

namespace UtilsReadStreamTest
{
	TEST_CLASS(TiedStreamTest)
	{
	public:

		TEST_METHOD(WhenPromptIsPassed_ShouldInsertPromptToTiedStream)
		{
			// Given
			std::istringstream stream = getStream(expectedValue);
			std::ostringstream tiedStream = getTiedStream(stream);

			// When
			const std::optional<std::string> actualValue = utils::IO::readStream<std::string>(expectedPrompt, stringValidator, stream);

			// Then
			Assert::AreEqual(expectedValue, actualValue.value());
			Assert::AreEqual(expectedPrompt, tiedStream.str());
		}

		TEST_METHOD(WhenFailureIsEncountered_ShouldInsertWarningToTiedStream)
		{
			// Given
			std::istringstream stream = getStream(expectedValue);
			std::ostringstream tiedStream = getTiedStream(stream);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", intValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
			Assert::AreEqual(invalidMessage, tiedStream.str());
		}

		TEST_METHOD(WhenPromptIsPassedAndFailureIsEncountered_ShouldInsertPromptAndWarningToTiedStream)
		{
			// Given
			std::istringstream stream = getStream(expectedValue);
			std::ostringstream tiedStream = getTiedStream(stream);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>(expectedPrompt, intValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
			Assert::AreEqual(expectedPrompt + invalidMessage, tiedStream.str());
		}

	private:

		inline static const std::string expectedPrompt = "Prompt";
		inline static const std::string invalidMessage = utils::IO::_internal::wrongInputMessage;
		inline static const std::string expectedValue = "first line";
		inline static const std::function<bool(const int)> intValidator = [](const int) { return true; };
		inline static const std::function<bool(const std::string&)> stringValidator = [](const std::string&) { return true; };

	};
}
