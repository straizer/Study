#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(FillRangeTest)
	{
	public:

		TEST_METHOD(WhenFillFullyNotEmptyVector_ShouldFillAllElementsWithValue)
		{
			// Given
			jipp::JippVector vector{ 0, 1 };
			const int expectedSize = 2;
			const int expectedCapacity = 4;
			const int expectedValue = 17;

			// When
			vector.fill(0, vector.size(), expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			for (const int& actualValue : vector)
				Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenFillPartiallyNotEmptyVector_ShouldFillElementsInRangeWithValue)
		{
			// Given
			const std::initializer_list data{ 0, 1 };
			const int* const dataAccessor = data.begin();
			const int expectedSize = 2;
			const int expectedCapacity = 4;
			const int expectedValue = 17;
			jipp::JippVector vector = data;

			// When
			vector.fill(1, vector.size() - 1, expectedValue);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(dataAccessor[0], vector[0]);
			Assert::AreEqual(expectedValue, vector[1]);
		}

		TEST_METHOD(WhenStartNegative_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.fill(-1, 0, 17); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenStartEqualToSize_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.fill(vector.size(), 0, 17); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenStartGreaterThanSize_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.fill(vector.size() + 1, 0, 17); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenCountGreaterThanSize_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.fill(0, vector.size() + 1, 17); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenCountNegative_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.fill(0, -1, 17); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenFillEmptyVector_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector;

			// When
			const auto throwingCall = [&]() { vector.fill(0, 0, 17); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}
	};
}
