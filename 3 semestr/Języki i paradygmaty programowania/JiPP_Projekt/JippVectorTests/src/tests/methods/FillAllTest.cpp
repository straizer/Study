#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(FillAllTest)
	{
	public:

		TEST_METHOD(WhenNotEmptyVector_ShouldFillAllElementsWithValue)
		{
			// Given
			jipp::JippVector vector{ 0, 1 };
			const int expectedSize = 2;
			const int expectedCapacity = 4;
			const int expectedValue = 17;

			// When
			vector.fill(expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			for (const int& actualValue : vector)
				Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenEmptyVector_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector;

			// When
			const auto throwingCall = [&]() { vector.fill(17); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}
	};
}
