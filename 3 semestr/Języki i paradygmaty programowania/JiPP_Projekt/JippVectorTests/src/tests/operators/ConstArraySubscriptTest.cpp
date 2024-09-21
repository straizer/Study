#include "Common.hpp"

#include "JippVector.hpp"

namespace OperatorsTest
{
	TEST_CLASS(ConstArraySubscriptTest)
	{
	public:

		TEST_METHOD(WhenEmptyVector_ShouldThrowRuntimeError)
		{
			// Given
			const jipp::JippVector vector = std::initializer_list<int>{ };

			// When
			const auto throwingCall = [&]() { [[maybe_unused]] int _ = vector[0]; };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenSubscriptValidIndexes_ShouldAccessElements)
		{
			// Given
			const std::initializer_list expectedData{ 0, 1 };
			const int* const expectedDataAccessor = expectedData.begin();
			const jipp::JippVector vector = expectedData;

			// When & Then
			Assert::AreEqual(expectedDataAccessor[0], vector[0]);
			Assert::AreEqual(expectedDataAccessor[1], vector[1]);
		}

		TEST_METHOD(WhenSubscriptNegativeIndex_ShouldThrowRuntimeError)
		{
			// Given
			const jipp::JippVector vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { [[maybe_unused]] int _ = vector[-1]; };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenSubscriptEqualToSizeIndex_ShouldThrowRuntimeError)
		{
			// Given
			const jipp::JippVector vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { [[maybe_unused]] int _ = vector[2]; };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenSubscriptGreaterThanSizeIndex_ShouldThrowRuntimeError)
		{
			// Given
			const jipp::JippVector vector{ 0, 1 };

			// When
			const auto throwingCall = [&]() { [[maybe_unused]] int _ = vector[3]; };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}
	};
}
