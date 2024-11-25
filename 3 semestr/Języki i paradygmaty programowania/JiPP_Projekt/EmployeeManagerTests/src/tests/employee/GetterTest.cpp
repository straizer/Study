#include "Common.hpp"

#include "Employee.hpp"

namespace EmployeeTest
{
	TEST_CLASS(GetterTest)
	{
	public:

		TEST_METHOD(WhenGetName_ShouldReturnEmptyString)
		{
			// Given
			const std::string expectedValue = "";
			const jipp::Employee employee;

			// When
			const std::string actualValue = employee.getName();

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenGetSurname_ShouldReturnEmptyString)
		{
			// Given
			const std::string expectedValue = "";
			const jipp::Employee employee;

			// When
			const std::string actualValue = employee.getSurname();

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenGetAge_ShouldReturnZero)
		{
			// Given
			const unsigned short expectedValue = 0;
			const jipp::Employee employee;

			// When
			const unsigned short actualValue = employee.getAge();

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenGetSalary_ShouldReturnZero)
		{
			// Given
			const unsigned int expectedValue = 0;
			const jipp::Employee employee;

			// When
			const unsigned int actualValue = employee.getSalary();

			// Then
			Assert::AreEqual(expectedValue, actualValue);
		}

		TEST_METHOD(WhenIsValid_ShouldReturnFalse)
		{
			// Given
			const jipp::Employee employee;

			// When
			const bool actualValue = employee.isValid();

			// Then
			Assert::IsFalse(actualValue);
		}
	};
}
