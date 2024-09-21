#include "Common.hpp"
#include <algorithm>

#include "JippVector.hpp"

namespace OthersTest
{
	TEST_CLASS(RangesViewsTest)
	{
	public:

		TEST_METHOD(WhenPipeToViews_ShouldNotThrow)
		{
			// Given
			const jipp::JippVector vector{ 0, 1, 2, 3, 4 };
			const int* iterator = vector.begin() + 1;

			// When
			auto actualView = vector | std::views::drop(1);

			// Then
			for (const int& actualValue : actualView)
			{
				int expectedValue = *iterator++;
				Assert::AreEqual(expectedValue, actualValue);
			}
		}

		TEST_METHOD(WhenUsedInRangesAlgorithm_ShouldNotThrow)
		{
			// Given
			const jipp::JippVector vector{ 0, 1, 2, 3, 4 };
			const int expectedValue = 10;

			// When
			auto actualValue = std::ranges::fold_left(vector, 0, std::plus());

			// Then				
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenPipeFromViews_ShouldCreateNewVector)
		{
			// Given
			const jipp::JippVector vector{ 0, 1, 2, 3, 4 };
			const int* iterator = vector.begin() + 1;

			// When
			jipp::JippVector<int> newVector = 
				vector |
				std::views::drop(1) |
				std::ranges::to<jipp::JippVector<int>>();

			// Then
			for (const int& actualValue : newVector)
			{
				int expectedValue = *iterator++;
				Assert::AreEqual(expectedValue, actualValue);
			}
		}
	};
}
