#include "utils-test/GetInputCommon.hpp"

namespace UtilsWriteStreamTest
{
	TEST_CLASS(InvalidWriteStreamTest)
	{
	public:


		TEST_METHOD(WhenStreamHasEofBitSet_ShouldReturnNullOptional)
		{
			// Given
			const std::ios_base::iostate expectedState = std::ios::eofbit;
			std::ostringstream stream;
			stream.setstate(expectedState);

			// When
			const auto throwingCall = [&]() { utils::IO::writeStream(0, stream); };

			// Then
			Assert::ExpectException<std::ios_base::failure>(throwingCall);
			Assert::AreEqual(stream.rdstate(), expectedState);
		}

		TEST_METHOD(WhenStreamHasFailBitSet_ShouldThrowIosBaseFailure)
		{
			// Given
			const std::ios_base::iostate expectedState = std::ios::failbit;
			std::ostringstream stream;
			stream.setstate(expectedState);

			// When
			const auto throwingCall = [&]() { utils::IO::writeStream(0, stream); };

			// Then
			Assert::ExpectException<std::ios_base::failure>(throwingCall);
			Assert::AreEqual(stream.rdstate(), expectedState);
		}

		TEST_METHOD(WhenStreamHasBadBitSet_ShouldThrowIosBaseFailure)
		{
			// Given
			const std::ios_base::iostate expectedState = std::ios::badbit;
			std::ostringstream stream;
			stream.setstate(expectedState);

			// When
			const auto throwingCall = [&]() { utils::IO::writeStream(0, stream); };

			// Then
			Assert::ExpectException<std::ios_base::failure>(throwingCall);
			Assert::AreEqual(stream.rdstate(), expectedState);
		}

	};
}
