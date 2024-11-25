#include "Common.hpp"

#include "JippVector.hpp"

namespace ConstructorsTest
{
	TEST_CLASS(CopyTest)
	{
	public:

		TEST_METHOD(WhenImplicit_ShouldCreateDefaultContainer)
		{
			// Given
			const jipp::JippVector<int> source{ 0, 1 };
			const int expectedSize = 2;
			const int expectedCapacity = 4;

			// When
			const jipp::JippVector vector = source;

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			for (int i = 0; i != vector.size(); i++)
			{
				Assert::AreEqual(source[i], vector[i]);
				Assert::AreNotSame(source[i], vector[i]);
			}
		}

		TEST_METHOD(WhenExplicit_ShouldCreateDefaultContainer)
		{
			// Given
			const jipp::JippVector<int> source{ 0, 1 };
			const int expectedSize = 2;
			const int expectedCapacity = 4;

			// When
			const jipp::JippVector vector(source);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			for (int i = 0; i != vector.size(); i++)
			{
				Assert::AreEqual(source[i], vector[i]);
				Assert::AreNotSame(source[i], vector[i]);
			}
		}
	};
}
