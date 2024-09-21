#include "Common.hpp"
#include "ThrowingClass.hpp"

#include "JippVector.hpp"

namespace ConstructorsTest
{
	TEST_CLASS(DefaultTest)
	{
	public:

		TEST_METHOD(WhenImplicit_ShouldCreateDefaultContainer)
		{
			// Given
			const int expectedSize = 0;
			const int expectedCapacity = 4;

			// When
			const jipp::JippVector<int> vector;

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
		}

		TEST_METHOD(WhenExplicit_ShouldCreateDefaultContainer)
		{
			// Given
			const int expectedSize = 0;
			const int expectedCapacity = 4;

			// When
			const jipp::JippVector vector = jipp::JippVector<int>();

			// Then
			Assert::AreEqual(expectedSize, vector.size());
			Assert::AreEqual(expectedCapacity, vector.capacity());
		}

		TEST_METHOD(WhenConstructorElementThrows_ShouldThrowRuntimeError)
		{
			// Given & When
			const auto throwingCall = []() { jipp::JippVector<ThrowingClass> vector; };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}
	};
}
