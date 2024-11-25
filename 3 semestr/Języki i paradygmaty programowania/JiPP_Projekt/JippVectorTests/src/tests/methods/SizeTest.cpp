#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(SizeTest)
	{
	public:

		TEST_METHOD(WhenEmptyVector_Returns0)
		{
			// Given
			const jipp::JippVector vector = std::initializer_list<int>{ };
			const int expectedSize = 0;

			// When
			const int actualSize = vector.size();

			// Then
			Assert::AreEqual(expectedSize, actualSize);
		}

		TEST_METHOD(When2ElementVector_Returns2)
		{
			// Given
			const jipp::JippVector vector{ 0, 1 };
			const int expectedSize = 2;

			// When
			const int actualSize = vector.size();

			// Then
			Assert::AreEqual(expectedSize, actualSize);
		}

		TEST_METHOD(When4ElementVector_Returns4)
		{
			// Given
			const jipp::JippVector vector{ 0, 1, 2, 3 };
			const int expectedSize = 4;

			// When
			const int actualSize = vector.size();

			// Then
			Assert::AreEqual(expectedSize, actualSize);
		}

		TEST_METHOD(When5ElementVector_Returns5)
		{
			// Given
			const jipp::JippVector vector{ 0, 1, 2, 3, 4 };
			const int expectedSize = 5;

			// When
			const int actualSize = vector.size();

			// Then
			Assert::AreEqual(expectedSize, actualSize);
		}
	};
}
