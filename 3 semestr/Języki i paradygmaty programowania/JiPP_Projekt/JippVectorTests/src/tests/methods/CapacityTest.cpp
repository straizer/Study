#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(CapacityTest)
	{
	public:

		TEST_METHOD(WhenEmptyVector_Returns4)
		{
			// Given
			const jipp::JippVector vector = std::initializer_list<int>{ };
			const int expectedCapacity = 4;

			// When
			const int actualCapacity = vector.capacity();

			// Then
			Assert::AreEqual(expectedCapacity, actualCapacity);
		}

		TEST_METHOD(WhenLessThan4ElementVector_Returns4)
		{
			// Given
			const jipp::JippVector vector{ 0, 1 };
			const int expectedCapacity = 4;

			// When
			const int actualCapacity = vector.capacity();

			// Then
			Assert::AreEqual(expectedCapacity, actualCapacity);
		}

		TEST_METHOD(When4ElementVector_Returns8)
		{
			// Given
			const jipp::JippVector vector{ 0, 1, 2, 3 };
			const int expectedCapacity = 8;

			// When
			const int actualCapacity = vector.capacity();

			// Then
			Assert::AreEqual(expectedCapacity, actualCapacity);
		}

		TEST_METHOD(WhenGreaterThan4LessThan8ElementVector_Returns8)
		{
			// Given
			const jipp::JippVector vector{ 0, 1, 2, 3, 4 };
			const int expectedCapacity = 8;

			// When
			const int actualCapacity = vector.capacity();

			// Then
			Assert::AreEqual(expectedCapacity, actualCapacity);
		}
	};
}
