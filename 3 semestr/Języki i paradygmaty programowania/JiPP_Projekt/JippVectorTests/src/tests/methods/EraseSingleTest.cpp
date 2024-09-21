#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(EraseSingleTest)
	{
	public:

		TEST_METHOD(WhenEraseNotEmptyVector_ShouldEraseSingleElement)
		{
			// Given
			const std::initializer_list data{ 0, 1, 2 };
			const int* const dataAccessor = data.begin();
			jipp::JippVector vector = data;
			const int expectedSize = vector.size() - 1;
			const int expectedCapacity = vector.capacity();

			// When
			vector.erase(1);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(dataAccessor[0], vector[0]);
			Assert::AreEqual(dataAccessor[2], vector[1]);
		}

		TEST_METHOD(WhenEraseFewTimesNotEmptyVector_ShouldEraseSingleElementAndReallocate)
		{
			// Given
			const std::initializer_list data{ 0, 1, 2, 3, 4 };
			const int* const dataAccessor = data.begin();
			jipp::JippVector vector = data;
			const int expectedSize = vector.size() - 4;
			const int expectedCapacity = vector.capacity() / 2;

			// When
			vector.erase(1);
			vector.erase(1);
			vector.erase(1);
			vector.erase(1);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			Assert::AreEqual(dataAccessor[0], vector[0]);
		}
		
		TEST_METHOD(WhenIndexNegative_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.erase(-1); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenIndexEqualToSize_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.erase(vector.size()); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenIndexGreaterThanSize_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.erase(vector.size() + 1); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenEraseEmptyVector_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector;

			// When
			const auto throwingCall = [&]() { vector.erase(0); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}
	};
}
