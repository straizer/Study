#include "Employee.hpp"

#include <iostream>
#include <iomanip>

namespace jipp
{
	[[nodiscard]] auto Employee::getName(void) const noexcept -> const std::string&
	{
		return m_name;
	}

	[[nodiscard]] auto Employee::getSurname(void) const noexcept -> const std::string&
	{
		return m_surname;
	}

	[[nodiscard]] auto Employee::getAge(void) const noexcept -> unsigned short
	{
		return m_age;
	}

	[[nodiscard]] auto Employee::getSalary(void) const noexcept -> unsigned int
	{
		return m_salary;
	}

	[[maybe_unused]] auto Employee::isValid(void) const noexcept -> bool
	{
		return m_valid;
	}

	[[maybe_unused]] auto Employee::setAge(const unsigned short age) noexcept -> Employee&
	{
		m_age = age;
		return *this;
	}

	[[maybe_unused]] auto Employee::setSalary(const unsigned int salary) noexcept -> Employee&
	{
		m_salary = salary;
		return *this;
	}

	[[maybe_unused]] auto Employee::setValid(void) noexcept -> Employee&
	{
		m_valid = true;
		return *this;
	}

	auto Employee::writeBinary(std::ostream& stream) const -> void
	{
		utils::IO::writeBinaryStream(stream, m_name.size());
		utils::IO::writeBinaryStream(stream, *m_name.c_str(), m_name.size());
		utils::IO::writeBinaryStream(stream, m_surname.size());
		utils::IO::writeBinaryStream(stream, *m_surname.c_str(), m_surname.size());
		utils::IO::writeBinaryStream(stream, m_age);
		utils::IO::writeBinaryStream(stream, m_salary);
		utils::IO::writeBinaryStream(stream, m_valid);
	}

	auto Employee::readBinary(std::istream& stream) -> void
	{
		std::size_t size;
		utils::IO::readBinaryStream(stream, size);
		m_name = std::string(size, ' ');
		utils::IO::readBinaryStream(stream, *m_name.data(), m_name.size());
		utils::IO::readBinaryStream(stream, size);
		m_surname = std::string(size, ' ');
		utils::IO::readBinaryStream(stream, *m_surname.data(), m_surname.size());
		utils::IO::readBinaryStream(stream, m_age);
		utils::IO::readBinaryStream(stream, m_salary);
		utils::IO::readBinaryStream(stream, m_valid);
	}

	[[nodiscard]] std::ostream& operator<<(std::ostream& stream, const Employee& employee)
	{
		if (!employee.m_valid)
			return stream << "Invalid employee" << std::endl;
		return stream << std::left
			<< "Name: " << std::setw(15) << employee.m_name << "|   "
			<< "Surname: " << std::setw(15) << employee.m_surname << "|   "
			<< "Age: " << std::setw(5) << employee.m_age << "|   "
			<< "Salary: " << std::setw(10) << employee.m_salary << std::endl;
	}
}
