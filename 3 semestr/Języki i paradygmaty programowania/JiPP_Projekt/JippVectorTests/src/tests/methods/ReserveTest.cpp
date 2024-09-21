#include "Common.hpp"

#include "JippVector.hpp"

namespace MethodsTest
{
	TEST_CLASS(ReserveTest)
	{
	public:

		TEST_METHOD(WhenReserveNotEmptyVector_ShouldIncreaseCapacity)
		{
			// Given
			const std::initializer_list data{ 0, 1 };
			const int* const dataAccessor = data.begin();
			jipp::JippVector vector = data;
			const int expectedSize = vector.size();
			const int expectedCapacity = 20;

			// When
			vector.reserve(expectedCapacity);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			for (int i = 0; i != vector.size(); i++)
				Assert::AreEqual(dataAccessor[i], vector[i]);
		}

		TEST_METHOD(WhenReserveEmptyVector_ShouldIncreaseCapacity)
		{
			// Given
			jipp::JippVector<int> vector;
			const int expectedSize = vector.size();
			const int expectedCapacity = 20;

			// When
			vector.reserve(expectedCapacity);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
		}

		TEST_METHOD(WhenReserveEqualToCapacity_ShouldNotIncreaseCapacity)
		{
			// Given
			const std::initializer_list data{ 0, 1 };
			const int* const dataAccessor = data.begin();
			jipp::JippVector vector = data;
			const int expectedSize = vector.size();
			const int expectedCapacity = vector.capacity();

			// When
			vector.reserve(expectedCapacity);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			for (int i = 0; i != vector.size(); i++)
				Assert::AreEqual(dataAccessor[i], vector[i]);
		}

		TEST_METHOD(WhenReserveLessThanCapacity_ShouldNotIncreaseCapacity)
		{
			// Given
			const std::initializer_list data{ 0, 1 };
			const int* const dataAccessor = data.begin();
			jipp::JippVector vector = data;
			const int expectedSize = vector.size();
			const int expectedCapacity = vector.capacity();

			// When
			vector.reserve(expectedCapacity - 1);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
			for (int i = 0; i != vector.size(); i++)
				Assert::AreEqual(dataAccessor[i], vector[i]);
		}

		TEST_METHOD(WhenReserveLessThanSize_ShouldThrowRuntimeError)
		{
			// Given
			jipp::JippVector<int> vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { vector.reserve(vector.size() - 1); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}
	};
}
