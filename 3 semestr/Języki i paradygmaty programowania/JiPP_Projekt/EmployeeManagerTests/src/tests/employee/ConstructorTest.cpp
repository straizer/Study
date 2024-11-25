#include "Common.hpp"

#include "Employee.hpp"

namespace EmployeeTest
{
	TEST_CLASS(ConstructorTest)
	{
	public:

		TEST_METHOD(WhenNotDefaultConstructorLvalueArguments_ShouldCreateValidEmployee)
		{
			// Given
			const std::string expectedName = "foo";
			const std::string expectedSurname = "bar";
			const unsigned short expectedAge = 69;
			const unsigned int expectedSalary = 420;

			// When
			const jipp::Employee employee{ expectedName, expectedSurname, expectedAge, expectedSalary };

			// Then
			Assert::AreEqual(expectedName, employee.getName());
			Assert::AreEqual(expectedSurname, employee.getSurname());
			Assert::AreEqual(expectedAge, employee.getAge());
			Assert::AreEqual(expectedSalary, employee.getSalary());
			Assert::IsTrue(employee.isValid());
		}

		TEST_METHOD(WhenNotDefaultConstructorRvalueArguments_ShouldCreateValidEmployee)
		{
			// Given
			const std::string expectedName = "foo";
			const std::string expectedSurname = "bar";
			const unsigned short expectedAge = 69;
			const unsigned int expectedSalary = 420;

			// When
			const jipp::Employee employee{ "foo", "bar", 69, 420 };

			// Then
			Assert::AreEqual(expectedName, employee.getName());
			Assert::AreEqual(expectedSurname, employee.getSurname());
			Assert::AreEqual(expectedAge, employee.getAge());
			Assert::AreEqual(expectedSalary, employee.getSalary());
			Assert::IsTrue(employee.isValid());
		}
	};
}
