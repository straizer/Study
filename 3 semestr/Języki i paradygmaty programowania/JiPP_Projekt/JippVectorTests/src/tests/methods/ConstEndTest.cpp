#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(ConstEndTest)
	{
	public:

		TEST_METHOD(WhenNotEmptyVector_ReturnsPointerToElementAfterLastElement)
		{
			// Given
			const jipp::JippVector vector{ 0, 1 };
			for (int i = 0; i != vector.size(); i++)
			{
				const int* const expectedIteator = &vector[i];

			// When
				const int* const actualIteator = vector.end() - vector.size() + i;

			// Then
				Assert::AreEqual(expectedIteator, actualIteator);
			}
		}
	};
}
