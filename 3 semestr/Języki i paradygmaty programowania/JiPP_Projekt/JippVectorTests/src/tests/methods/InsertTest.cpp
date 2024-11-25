#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(InsertTest)
	{
	public:

		TEST_METHOD(WhenIndexNegative_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.insert(-1, 17); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenIndexGreaterThanSize_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.insert(vector.size() + 1, 17); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenIndexEqualToSize_ShouldAddElementToEnd)
		{
			// Given
			const std::initializer_list data{ 0, 1 };
			const int* const dataAccessor = data.begin();
			jipp::JippVector vector = data;
			const int expectedSize = vector.size() + 1;
			const int expectedCapacity = vector.capacity();
			const int expectedValue = 17;

			// When
			vector.insert(vector.size(), expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(dataAccessor[0], vector[0]);
			Assert::AreEqual(dataAccessor[1], vector[1]);
			Assert::AreEqual(expectedValue, vector[2]);
		}

		TEST_METHOD(WhenIndexEqualToSizeNoSpaceAtEnd_ShouldReallocateAndAddElementToEnd)
		{
			// Given
			const std::initializer_list data{ 0, 1 };
			const int* const dataAccessor = data.begin();
			const int extraElement = 2;
			jipp::JippVector vector = data;
			vector.pushBack(extraElement);
			const int expectedSize = vector.size() + 1;
			const int expectedCapacity = vector.capacity() * 2;
			const int expectedValue = 17;

			// When
			vector.insert(vector.size(), expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(dataAccessor[0], vector[0]);
			Assert::AreEqual(dataAccessor[1], vector[1]);
			Assert::AreEqual(extraElement, vector[2]);
			Assert::AreEqual(expectedValue, vector[3]);
		}

		TEST_METHOD(WhenIndexEqualToZero_ShouldAddElementToFront)
		{
			// Given
			const std::initializer_list data{ 0, 1 };
			const int* const dataAccessor = data.begin();
			jipp::JippVector vector = data;
			const int expectedSize = vector.size() + 1;
			const int expectedCapacity = vector.capacity();
			const int expectedValue = 17;

			// When
			vector.insert(0, expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(expectedValue, vector[0]);
			Assert::AreEqual(dataAccessor[0], vector[1]);
			Assert::AreEqual(dataAccessor[1], vector[2]);
		}

		TEST_METHOD(WhenIndexEqualToZeroNoSpaceAtFront_ShouldReallocateAndAddElementToFront)
		{
			// Given
			const std::initializer_list data{ 0, 1 };
			const int* const dataAccessor = data.begin();
			const int extraElement = 2;
			jipp::JippVector vector = data;
			vector.pushFront(extraElement);
			const int expectedSize = vector.size() + 1;
			const int expectedCapacity = vector.capacity() * 2;
			const int expectedValue = 17;

			// When
			vector.insert(0, expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(expectedValue, vector[0]);
			Assert::AreEqual(extraElement, vector[1]);
			Assert::AreEqual(dataAccessor[0], vector[2]);
			Assert::AreEqual(dataAccessor[1], vector[3]);
		}

		TEST_METHOD(WhenSpaceAvailableInFront_ShouldAddElement)
		{
			// Given
			const std::initializer_list data{ 0, 1 };
			const int* const dataAccessor = data.begin();
			jipp::JippVector vector = data;
			const int expectedSize = vector.size() + 1;
			const int expectedCapacity = vector.capacity();
			const int expectedValue = 17;

			// When
			vector.insert(1, expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(dataAccessor[0], vector[0]);
			Assert::AreEqual(expectedValue, vector[1]);
			Assert::AreEqual(dataAccessor[1], vector[2]);
		}

		TEST_METHOD(WhenSpaceNotAvailableInFront_ShouldAddElement)
		{
			// Given
			const std::initializer_list data{ 0, 1 };
			const int* const dataAccessor = data.begin();
			const int extraElement = 2;
			jipp::JippVector vector = data;
			vector.pushFront(extraElement);
			const int expectedSize = vector.size() + 1;
			const int expectedCapacity = vector.capacity();
			const int expectedValue = 17;

			// When
			vector.insert(1, expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(extraElement, vector[0]);
			Assert::AreEqual(expectedValue, vector[1]);
			Assert::AreEqual(dataAccessor[0], vector[2]);
			Assert::AreEqual(dataAccessor[1], vector[3]);
		}

		TEST_METHOD(WhenSpaceNotAvailableInFrontAndBack_ShouldReallocateAndAddElement)
		{
			// Given
			const std::initializer_list data{ 0, 1, 2 };
			const int* const dataAccessor = data.begin();
			const int extraElement = 3;
			jipp::JippVector vector = data;
			vector.pushBack(extraElement);
			const int expectedSize = vector.size() + 1;
			const int expectedCapacity = vector.capacity() * 2;
			const int expectedValue = 17;

			// When
			vector.insert(1, expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(dataAccessor[0], vector[0]);
			Assert::AreEqual(expectedValue, vector[1]);
			Assert::AreEqual(dataAccessor[1], vector[2]);
			Assert::AreEqual(dataAccessor[2], vector[3]);
			Assert::AreEqual(extraElement, vector[4]);
		}
	};
}
