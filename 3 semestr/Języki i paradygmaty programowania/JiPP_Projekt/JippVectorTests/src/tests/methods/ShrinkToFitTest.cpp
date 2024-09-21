#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(ShrinkToFitTest)
	{
	public:

		TEST_METHOD(NotEmptyVector_ShouldShrinkToSize)
		{
			// Given
			jipp::JippVector vector{ 0, 1, 2, 3, 4 };
			const int expectedSize = 5;
			const int expectedCapacity = 5;

			// When
			vector.shrinkToFit();

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
		}

		TEST_METHOD(EmptyVector_ShouldShrinkToSize)
		{
			// Given
			jipp::JippVector<int> vector;
			const int expectedSize = 0;
			const int expectedCapacity = 1;

			// When
			vector.shrinkToFit();

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
		}
	};
}
