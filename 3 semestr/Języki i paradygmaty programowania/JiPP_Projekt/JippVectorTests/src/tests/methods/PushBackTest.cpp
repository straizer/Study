#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(PushBackTest)
	{
	public:

		TEST_METHOD(WhenEmptyVector_ShouldAddElement)
		{
			// Given
			jipp::JippVector<int> vector;
			const int expectedSize = vector.size() + 1;
			const int expectedCapacity = vector.capacity();
			const int expectedValue = 17;

			// When
			vector.pushBack(expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(expectedValue, vector[0]);
		}

		TEST_METHOD(WhenCapacityAvailable_ShouldAddElement)
		{
			// Given
			const std::initializer_list data{ 0, 1 };
			const int* const dataAccessor = data.begin();
			jipp::JippVector vector = data;
			const int expectedSize = vector.size() + 1;
			const int expectedCapacity = vector.capacity();
			const int expectedValue = 17;

			// When
			vector.pushBack(expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			for (int i = 0; i != expectedSize - 1; i++)
				Assert::AreEqual(dataAccessor[i], vector[i]);
			Assert::AreEqual(expectedValue, vector[expectedSize - 1]);
		}

		TEST_METHOD(WhenCapacityNotAvailable_ShouldReallocateAndAddElement)
		{
			// Given
			const std::initializer_list data{ 0, 1, 2 };
			const int* const dataAccessor = data.begin();
			jipp::JippVector vector = data;
			const int expectedSize = vector.size() + 2;
			const int expectedCapacity = vector.capacity() * 2;
			const int expectedValue = 17;
			vector.pushBack(expectedValue);

			// When
			vector.pushBack(expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			for (int i = 0; i != expectedSize - 2; i++)
				Assert::AreEqual(dataAccessor[i], vector[i]);
			Assert::AreEqual(expectedValue, vector[expectedSize - 2]);
			Assert::AreEqual(expectedValue, vector[expectedSize - 1]);
		}
	};
}
