#include "Common.hpp"

#include "utils/Finally.hpp"

namespace UtilsFinallyTest
{
	TEST_CLASS(FinallyTest)
	{
	public:

		TEST_METHOD(WhenExitingBlock_ShouldExecuteLambda)
		{
			// Given
			int actualValue = 0;
			int expectedValue = 1;

			// When
			{
				auto _ = finally({ actualValue = expectedValue; });
			}

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenNotExitingBlock_ShouldNotExecuteLambda)
		{
			// Given
			int actualValue = 0;
			int expectedValue = 0;
			int newValue = 1;

			// When
			auto _ = finally({ actualValue = newValue; });

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenForceCallDestructor_ShouldExecuteLambda)
		{
			// Given
			int actualValue = 0;
			int expectedValue = 1;

			// When
			auto _ = finally({ actualValue = expectedValue; });
			_.~FinalAction();

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenDisable_ShouldNotExecuteLambda)
		{
			// Given
			int actualValue = 0;
			int expectedValue = 0;
			int newValue = 1;

			// When
			{
				auto _ = finally({ actualValue = newValue; });
				_.disable();
			}

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}
	};
}
