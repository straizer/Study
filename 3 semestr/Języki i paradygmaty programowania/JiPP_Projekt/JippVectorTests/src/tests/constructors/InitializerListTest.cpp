#include "Common.hpp"
#include "ThrowingClass.hpp"

#include "JippVector.hpp"

namespace ConstructorsTest
{
	TEST_CLASS(InitializerListTest)
	{
	public:

		TEST_METHOD(WhenEmptyList_ShouldCreate4ElementInternalBuffer)
		{
			// Given & When
			const jipp::JippVector vector = std::initializer_list<int>{ };

			// Then
			const int* const actualData = jipp::_internal::_extract(vector);

			// Assert
			Assert::AreNotEqual(0, actualData[0]);
			Assert::AreNotEqual(0, actualData[1]);
			Assert::AreNotEqual(0, actualData[2]);
			Assert::AreNotEqual(0, actualData[3]);
		}

		TEST_METHOD(WhenLessThan4ElementList_ShouldCreate4ElementInternalBufferAndPopulate)
		{
			// Given
			const std::initializer_list expectedData{ 0, 1 };
			const int* const expectedDataAccessor = expectedData.begin();

			// When
			const jipp::JippVector vector = expectedData;

			// Then
			const int* const actualData = jipp::_internal::_extract(vector);

			// Assert
			Assert::AreNotEqual(0, actualData[0]);
			Assert::AreEqual(expectedDataAccessor[0], actualData[1]);
			Assert::AreEqual(expectedDataAccessor[1], actualData[2]);
			Assert::AreNotEqual(0, actualData[3]);
		}

		TEST_METHOD(When4ElementList_ShouldCreate8ElementInternalBufferAndPopulate)
		{
			// Given
			const std::initializer_list expectedData{ 0, 1, 2, 3 };
			const int* const expectedDataAccessor = expectedData.begin();

			// When
			const jipp::JippVector vector = expectedData;

			// Then
			const int* const actualData = jipp::_internal::_extract(vector);

			// Assert
			Assert::AreNotEqual(0, actualData[0]);
			Assert::AreNotEqual(0, actualData[1]);
			Assert::AreEqual(expectedDataAccessor[0], actualData[2]);
			Assert::AreEqual(expectedDataAccessor[1], actualData[3]);
			Assert::AreEqual(expectedDataAccessor[2], actualData[4]);
			Assert::AreEqual(expectedDataAccessor[3], actualData[5]);
			Assert::AreNotEqual(0, actualData[6]);
			Assert::AreNotEqual(0, actualData[7]);
		}

		TEST_METHOD(WhenGreaterThan4LessThan8ElementList_ShouldCreate8ElementInternalBufferAndPopulate)
		{
			// Given
			const std::initializer_list expectedData{ 0, 1, 2, 3, 4 };
			const int* const expectedDataAccessor = expectedData.begin();

			// When
			const jipp::JippVector vector = expectedData;

			// Then
			const int* const actualData = jipp::_internal::_extract(vector);

			// Assert
			Assert::AreNotEqual(0, actualData[0]);
			Assert::AreEqual(expectedDataAccessor[0], actualData[1]);
			Assert::AreEqual(expectedDataAccessor[1], actualData[2]);
			Assert::AreEqual(expectedDataAccessor[2], actualData[3]);
			Assert::AreEqual(expectedDataAccessor[3], actualData[4]);
			Assert::AreEqual(expectedDataAccessor[4], actualData[5]);
			Assert::AreNotEqual(0, actualData[6]);
			Assert::AreNotEqual(0, actualData[7]);
		}

		TEST_METHOD(WhenGreaterThan4LessThan8ElementListExplicit_ShouldCreate8ElementInternalBufferAndPopulate)
		{
			// Given
			const std::initializer_list expectedData{ 0, 1, 2, 3, 4 };
			const int* const expectedDataAccessor = expectedData.begin();

			// When
			const jipp::JippVector vector = jipp::JippVector(expectedData);

			// Then
			const int* const actualData = jipp::_internal::_extract(vector);

			// Assert
			Assert::AreNotEqual(0, actualData[0]);
			Assert::AreEqual(expectedDataAccessor[0], actualData[1]);
			Assert::AreEqual(expectedDataAccessor[1], actualData[2]);
			Assert::AreEqual(expectedDataAccessor[2], actualData[3]);
			Assert::AreEqual(expectedDataAccessor[3], actualData[4]);
			Assert::AreEqual(expectedDataAccessor[4], actualData[5]);
			Assert::AreNotEqual(0, actualData[6]);
			Assert::AreNotEqual(0, actualData[7]);
		}

		TEST_METHOD(WhenConstructorElementThrows_ShouldThrowRuntimeError)
		{
			// Given & When
			const auto throwingCall = []() { jipp::JippVector vector = std::initializer_list<ThrowingClass>{ }; };

			// Then
			Assert::ExpectException<std::runtime_error>(throwingCall);
		}
	};
}
