#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(IsEmptyTest)
	{
	public:

		TEST_METHOD(WhenEmptyVector_ReturnsTrue)
		{
			// Given
			const jipp::JippVector<int> vector;
			const bool expectedEmpty = true;

			// When
			const bool actualEmpty = vector.isEmpty();

			// Then
			Assert::AreEqual(expectedEmpty, actualEmpty);
		}

		TEST_METHOD(WhenNonEmptyVector_ReturnsFalse)
		{
			// Given
			const jipp::JippVector vector{ 0 };
			const bool expectedEmpty = false;

			// When
			const bool actualEmpty = vector.isEmpty();

			// Then
			Assert::AreEqual(expectedEmpty, actualEmpty);
		}
	};
}
