#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(EraseRangeTest)
	{
	public:

		TEST_METHOD(WhenEraseFullyNotEmptyVector_ShouldEraseAllElementsAndReallocate)
		{
			// Given
			jipp::JippVector vector{ 0, 1, 2, 3, 4, 5 };
			const int expectedSize = 0;
			const int expectedCapacity = 4;

			// When
			vector.erase(0, vector.size());

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
		}

		TEST_METHOD(WhenErasePartiallyNotEmptyVector_ShouldEraseElementsInRange)
		{
			// Given
			const std::initializer_list data{ 0, 1, 2, 3, 4, 5 };
			const int* const dataAccessor = data.begin();
			const int expectedSize = 4;
			const int expectedCapacity = 8;
			jipp::JippVector vector = data;

			// When
			vector.erase(2, 2);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(dataAccessor[0], vector[0]);
			Assert::AreEqual(dataAccessor[1], vector[1]);
			Assert::AreEqual(dataAccessor[4], vector[2]);
			Assert::AreEqual(dataAccessor[5], vector[3]);
		}

		TEST_METHOD(WhenErasePartiallyNotEmptyVector_ShouldEraseElementsInRangeAndReallocate)
		{
			// Given
			const std::initializer_list data{ 0, 1, 2, 3, 4, 5 };
			const int* const dataAccessor = data.begin();
			const int expectedSize = 1;
			const int expectedCapacity = 4;
			jipp::JippVector vector = data;

			// When
			vector.erase(0, 5);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(dataAccessor[5], vector[0]);
		}

		TEST_METHOD(WhenStartNegative_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.erase(-1, 0); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenStartEqualToSize_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.erase(vector.size(), 0); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenStartGreaterThanSize_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.erase(vector.size() + 1, 0); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenCountGreaterThanSize_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.erase(0, vector.size() + 1); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenCountNegative_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.erase(0, -1); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenCountEqualToZero_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.erase(0, 0); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenEraseEmptyVector_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector;

			// When
			const auto throwingCall = [&]() { vector.erase(0, 1); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}
	};
}
