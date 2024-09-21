#include "utils-test/GetInputCommon.hpp"

namespace UtilsWriteStreamTest
{
	TEST_CLASS(WriteStreamTest)
	{
	public:


		TEST_METHOD(WhenInsertToStream_ShouldInsertValue)
		{
			// Given
			const std::string expectedValue = "42";
			std::ostringstream stream;

			// When
			utils::IO::writeStream(expectedValue, stream);

			// Then
			Assert::AreEqual(expectedValue, stream.str());
		}

		TEST_METHOD(WhenChainInsertToStream_ShouldInsertAllValues)
		{
			// Given
			const std::string expectedValue = "42foo";
			std::ostringstream stream;

			// When
			utils::IO::writeStream("foo", utils::IO::writeStream(42, stream));

			// Then
			Assert::AreEqual(expectedValue, stream.str());
		}
	};
}
