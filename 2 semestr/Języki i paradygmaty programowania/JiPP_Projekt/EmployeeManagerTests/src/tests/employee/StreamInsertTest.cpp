#include "Common.hpp"

#include "Employee.hpp"

namespace EmployeeTest
{
	TEST_CLASS(StreamInsertTest)
	{
	public:

		TEST_METHOD(WhenInsertToStream_ShouldInsert)
		{
			// Given
			const std::string expectedValue = "Name: foo            |   Surname: bar            |   Age: 69   |   Salary: 420       ";
			const jipp::Employee employee{ "foo", "bar", 69, 420 };
			std::ostringstream stream;

			// When
			stream << employee;

			// Then
			Assert::AreEqual(expectedValue, stream.str());
		}
	};
}
