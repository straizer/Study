#include "Common.hpp"

#include "Employee.hpp"

namespace EmployeeTest
{
	TEST_CLASS(SetterTest)
	{
	public:

		TEST_METHOD(WhenSetNameLvalue_ShouldChangeName)
		{
			// Given
			const std::string expectedValue = "foo";
			jipp::Employee employee;

			// When
			employee.setName(expectedValue);

			// Then
			Assert::AreEqual(expectedValue, employee.getName());
		}

		TEST_METHOD(WhenSetNameRvalue_ShouldChangeName)
		{
			// Given
			const std::string expectedValue = "foo";
			jipp::Employee employee;

			// When
			employee.setName("foo");

			// Then
			Assert::AreEqual(expectedValue, employee.getName());
		}

		TEST_METHOD(WhenSetSurnameLvalue_ShouldChangeSurname)
		{
			// Given
			const std::string expectedValue = "foo";
			jipp::Employee employee;

			// When
			employee.setSurname(expectedValue);

			// Then
			Assert::AreEqual(expectedValue, employee.getSurname());
		}

		TEST_METHOD(WhenSetSurnameRvalue_ShouldChangeSurname)
		{
			// Given
			const std::string expectedValue = "foo";
			jipp::Employee employee;

			// When
			employee.setSurname("foo");

			// Then
			Assert::AreEqual(expectedValue, employee.getSurname());
		}

		TEST_METHOD(WhenSetAge_ShouldChangeAge)
		{
			// Given
			const unsigned short expectedValue = 42;
			jipp::Employee employee;

			// When
			employee.setAge(expectedValue);

			// Then
			Assert::AreEqual(expectedValue, employee.getAge());
		}

		TEST_METHOD(WhenSetSalary_ShouldChangeSalary)
		{
			// Given
			const unsigned int expectedValue = 42;
			jipp::Employee employee;

			// When
			employee.setSalary(expectedValue);

			// Then
			Assert::AreEqual(expectedValue, employee.getSalary());
		}

		TEST_METHOD(WhenSetValid_ShouldChangeStateToValid)
		{
			// Given
			jipp::Employee employee;

			// When
			employee.setValid();

			// Then
			Assert::IsTrue(employee.isValid());
		}

		TEST_METHOD(WhenChainSetters_ShouldChangeAllValues)
		{
			// Given
			const std::string expectedName = "foo";
			const std::string expectedSurname = "bar";
			const unsigned short expectedAge = 69;
			const unsigned int expectedSalary = 420;
			jipp::Employee employee;

			// When
			employee
				.setName(expectedName)
				.setSurname(expectedSurname)
				.setAge(expectedAge)
				.setSalary(expectedSalary)
				.setValid();

			// Then
			Assert::AreEqual(expectedName, employee.getName());
			Assert::AreEqual(expectedSurname, employee.getSurname());
			Assert::AreEqual(expectedAge, employee.getAge());
			Assert::AreEqual(expectedSalary, employee.getSalary());
			Assert::IsTrue(employee.isValid());
		}
	};
}
