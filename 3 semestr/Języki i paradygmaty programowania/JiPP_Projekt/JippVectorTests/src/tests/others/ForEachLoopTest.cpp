#include "Common.hpp"

#include "JippVector.hpp"

namespace OthersTest
{
	TEST_CLASS(ForEachLoopTest)
	{
	public:

#pragma warning(push)
#pragma warning(disable: 4702) // Disable warning - unreachable code
		TEST_METHOD(WhenEmptyVector_DoNotEnterLoop)
		{
			// Given
			jipp::JippVector<int> vector;

			// When
			for ([[maybe_unused]] const int& value : vector)

				// Then
				Assert::Fail();
		}
#pragma warning(pop)

		TEST_METHOD(WhenNotEmptyVector_ShouldAccessElements)
		{
			// Given
			jipp::JippVector vector{ 0, 1 };
			const int* iterator = vector.begin();
			int expectedValue = *iterator++;

			// When
			for (const int& actualValue : vector)
			{

				// Then
				Assert::AreEqual(expectedValue, actualValue);

				// And
				expectedValue = *iterator++;
			}
		}
	};
}
