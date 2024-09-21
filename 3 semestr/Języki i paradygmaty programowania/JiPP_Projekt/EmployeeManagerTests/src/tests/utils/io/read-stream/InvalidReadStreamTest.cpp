#include "utils-test/GetInputCommon.hpp"

namespace UtilsReadStreamTest
{
	TEST_CLASS(InvalidReadStreamTest)
	{
	public:


		TEST_METHOD(WhenStreamHasEofBitSet_ShouldReturnNullOptional)
		{
			// Given
			const std::ios_base::iostate expectedState = std::ios::eofbit;
			std::istringstream stream = getStream("", expectedState);

			// When
			const std::optional<int> actualValue = utils::IO::readStream<int>("", defaultValidator, stream);

			// Then
			Assert::IsFalse(actualValue.has_value());
			Assert::AreEqual(stream.rdstate(), expectedState);
		}

		TEST_METHOD(WhenStreamHasFailBitSet_ShouldThrowIosBaseFailure)
		{
			// Given
			const std::ios_base::iostate expectedState = std::ios::failbit;
			std::istringstream stream = getStream("", expectedState);

			// When
			const auto throwingCall = [&]() { [[maybe_unused]] auto _ = utils::IO::readStream<int>("", defaultValidator, stream); };

			// Then
			Assert::ExpectException<std::ios_base::failure>(throwingCall);
			Assert::AreEqual(stream.rdstate(), expectedState);
		}

		TEST_METHOD(WhenStreamHasBadBitSet_ShouldThrowIosBaseFailure)
		{
			// Given
			const std::ios_base::iostate expectedState = std::ios::badbit;
			std::istringstream stream = getStream("", expectedState);

			// When
			const auto throwingCall = [&]() { [[maybe_unused]] auto _ = utils::IO::readStream<int>("", defaultValidator, stream); };

			// Then
			Assert::ExpectException<std::ios_base::failure>(throwingCall);
			Assert::AreEqual(stream.rdstate(), expectedState);
		}

		TEST_METHOD(WhenTiedStreamHasEofBitSet_ShouldThrowIosBaseFailure)
		{
			// Given
			const std::ios_base::iostate expectedState = std::ios::eofbit;
			std::istringstream stream = getStream("");
			std::ostringstream tiedStream = getTiedStream(stream, expectedState);

			// When
			const auto throwingCall = [&]() { [[maybe_unused]] auto _ = utils::IO::readStream<int>("", defaultValidator, stream); };

			// Then
			Assert::ExpectException<std::ios_base::failure>(throwingCall);
			Assert::AreEqual(stream.rdstate(), std::ios::goodbit);
			Assert::AreEqual(stream.tie()->rdstate(), expectedState);
		}

		TEST_METHOD(WhenTiedStreamHasFailBitSet_ShouldThrowIosBaseFailure)
		{
			// Given
			const std::ios_base::iostate expectedState = std::ios::failbit;
			std::istringstream stream = getStream("");
			std::ostringstream tiedStream = getTiedStream(stream, expectedState);

			// When
			const auto throwingCall = [&]() { [[maybe_unused]] auto _ = utils::IO::readStream<int>("", defaultValidator, stream); };

			// Then
			Assert::ExpectException<std::ios_base::failure>(throwingCall);
			Assert::AreEqual(stream.rdstate(), std::ios::goodbit);
			Assert::AreEqual(stream.tie()->rdstate(), expectedState);
		}

		TEST_METHOD(WhenTiedStreamHasBadBitSet_ShouldThrowIosBaseFailure)
		{
			// Given
			const std::ios_base::iostate expectedState = std::ios::badbit;
			std::istringstream stream = getStream("");
			std::ostringstream tiedStream = getTiedStream(stream, expectedState);

			// When
			const auto throwingCall = [&]() { [[maybe_unused]] auto _ = utils::IO::readStream<int>("", defaultValidator, stream); };

			// Then
			Assert::ExpectException<std::ios_base::failure>(throwingCall);
			Assert::AreEqual(stream.rdstate(), std::ios::goodbit);
			Assert::AreEqual(stream.tie()->rdstate(), expectedState);
		}

	private:

		inline static const std::function<bool(const int)> defaultValidator = [](const int) { return true; };

	};
}
