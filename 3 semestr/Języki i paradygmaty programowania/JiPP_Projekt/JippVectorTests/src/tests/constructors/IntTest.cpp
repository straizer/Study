#include "Common.hpp"
#include "ThrowingClass.hpp"

#include "JippVector.hpp"

namespace ConstructorsTest
{
	TEST_CLASS(IntTest)
	{
	public:

		TEST_METHOD(WhenPositiveCapacity_ShouldCreateContainerWithGivenCapacity)
		{
			// Given
			const int expectedSize = 0;
			const int expectedCapacity = 1;

			// When
			const jipp::JippVector<int> vector(expectedCapacity);

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
		}

		TEST_METHOD(WhenZeroCapacity_ShouldThrowRuntimeError)
		{
			// Given & When
			const auto throwingCall = []() { jipp::JippVector<int> vector(0); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenNegativeCapacity_ShouldThrowRuntimeError)
		{
			// Given & When
			const auto throwingCall = []() { jipp::JippVector<int> vector(-1); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}

		TEST_METHOD(WhenConstructorElementThrows_ShouldThrowRuntimeError)
		{
			// Given & When
			const auto throwingCall = []() { jipp::JippVector<ThrowingClass> vector(1); };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}
	};
}
