#include "Common.hpp"
#include "ThrowingClass.hpp"
#include <bit>

#include "JippVector.hpp"

namespace ConstructorsTest
{
	TEST_CLASS(RangesTest)
	{
	public:

		TEST_METHOD(WhenEmptyRange_ShouldCreateDefaultContainer)
		{
			// Given
			const int expectedSize = 0;
			const int expectedCapacity = 4;

			// When
			const jipp::JippVector<int> vector = std::ranges::empty_view<int>();

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
		}

		TEST_METHOD(WhenNonEmptyRange_ShouldCreateContainerAndPopulate)
		{
			// Given
			const std::ranges::iota_view range{ 0, 10 };
			auto iterator = range.begin();
			const int expectedSize = 10;
			const int expectedCapacity = 16;

			// When
			const jipp::JippVector<int> vector = range;

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			for (const int& actualValue : vector)
			{
				int expectedValue = *iterator++;
				Assert::AreEqual(expectedValue, actualValue);
			}
		}

		TEST_METHOD(WhenConstructorElementThrows_ShouldThrowRuntimeError)
		{
			// Given & When
			const auto throwingCall = []() { jipp::JippVector<ThrowingClass> vector = std::ranges::empty_view<ThrowingClass>(); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}
	};
}
