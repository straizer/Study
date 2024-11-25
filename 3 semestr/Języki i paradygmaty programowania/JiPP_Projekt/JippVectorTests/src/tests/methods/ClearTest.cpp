#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(ClearTest)
	{
	public:

		TEST_METHOD(WhenNotEmptyVector_ShouldClearAndNotChangeCapacity)
		{
			// Given
			jipp::JippVector vector{ 0, 1 };
			const int expectedSize = 0;
			const int expectedCapacity = vector.capacity();

			// When
			vector.clear();

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
		}

		TEST_METHOD(WhenEmptyVector_ShouldClearAndNotChangeCapacity)
		{
			// Given
			jipp::JippVector<int> vector;
			const int expectedSize = 0;
			const int expectedCapacity = vector.capacity();

			// When
			vector.clear();

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
		}
	};
}
