#pragma once

#include <iosfwd>

#include "utils/IO.hpp"

/**
 * @brief Namespace containing final project for JiPP2 subject.
 */
namespace jipp
{
	/**
	 * @brief Represents Employee.
	 */
	class Employee
	{
	public:

		/**
		 * @brief Default constructor. Constructs Employee with default fields.
		 */
		[[nodiscard]] Employee(void) noexcept = default;

		/**
		 * @brief Constructs Employee with given values. After this constructor isValid() returns true.
		 * @tparam Name - type of name argument; must fulfill StringLike constraint
		 * @tparam Surname - type of surname argument; must fulfill StringLike constraint
		 * @param name - Employee name
		 * @param surname - Employee surname
		 * @param age - Employee age
		 * @param salary - Employee salary
		 */
		template <utils::concepts::StringLike Name, utils::concepts::StringLike Surname>
		[[nodiscard]] Employee(Name&& name, Surname&& surname, const unsigned short age, const unsigned int salary) noexcept;

		/**
		 * @brief Default copy destructor. Copies the Employee.
		 */
		Employee(const Employee&) noexcept = default;

		/**
		 * @brief Default copy assgnment operator. Assigns the Employee.
		 * TODO: Tests
		 */
		Employee& operator=(const Employee&) noexcept = default;

		/**
		 * @brief Default virtual destructor. Destructs the Employee.
		 * TODO: Tests
		 */
		virtual ~Employee(void) noexcept = default;


		/**
		 * @brief Gets name of the Employee.
		 * @return Employee name.
		 */
		[[nodiscard]] auto getName(void) const noexcept -> const std::string&;

		/**
		 * @brief Gets surname of the Employee.
		 * @return Employee surname.
		 */
		[[nodiscard]] auto getSurname(void) const noexcept -> const std::string&;

		/**
		 * @brief Gets age of the Employee.
		 * @return Employee age.
		 */
		[[nodiscard]] auto getAge(void) const noexcept -> unsigned short;

		/**
		 * @brief Gets salary of the Employee.
		 * @return Employee salary.
		 */
		[[nodiscard]] auto getSalary(void) const noexcept -> unsigned int;

		/**
		 * @brief Returns if the Employee is valid.
		 * @return true if Employee is valid; false otherwise.
		 */
		[[nodiscard]] auto isValid(void) const noexcept -> bool;


		/**
		 * @brief Sets name of the Employee. Allows setter chaining.
		 * @tparam Name - type of name argument; must fulfill StringLike constraint
		 * @param name - Employee name
		 * @return Self-reference.
		 */
		template <utils::concepts::StringLike Name>
		[[maybe_unused]] auto setName(Name&& name) noexcept -> Employee&;

		/**
		 * @brief Sets surname of the Employee. Allows setter chaining.
		 * @tparam Surname - type of surname argument; must fulfill StringLike constraint
		 * @param surname - Employee surname
		 * @return Self-reference.
		 */
		template <utils::concepts::StringLike Surname>
		[[maybe_unused]] auto setSurname(Surname&& surname) noexcept -> Employee&;

		/**
		 * @brief Sets age of the Employee. Allows setter chaining.
		 * @param age - Employee age
		 * @return Self-reference.
		 */
		[[maybe_unused]] auto setAge(const unsigned short age) noexcept -> Employee&;

		/**
		 * @brief Sets salary of the Employee. Allows setter chaining.
		 * @param salary - Employee salary
		 * @return Self-reference.
		 */
		[[maybe_unused]] auto setSalary(const unsigned int salary) noexcept -> Employee&;

		/**
		 * @brief Sets state of the Employee to valid. Allows setter chaining.
		 * @return Self-reference.
		 */
		[[maybe_unused]] auto setValid(void) noexcept -> Employee&;

		//TODO: Tests
		auto writeBinary(std::ostream& stream) const -> void;

		//TODO: Tests
		auto readBinary(std::istream& stream) -> void;

	private:

		/**
		 * @brief Employee name.
		 */
		std::string m_name{};

		/**
		 * @brief Employee surname.
		 */
		std::string m_surname{};

		/**
		 * @brief Employee age.
		 */
		unsigned short m_age{};

		/**
		 * @brief Employee salary.
		 */
		unsigned int m_salary{};

		/**
		 * @brief Employee state. Default is false.
		 */
		bool m_valid = false;


		/**
		 * @brief Inserts Employee to stream.
		 * @param stream - stream to insert the employee to
		 * @param employee - Employee to insert to stream
		 * @return Stream with inserted Employee.
		 */
		friend std::ostream& operator<<(std::ostream& stream, const Employee& employee);
	};

#pragma region Implementation

	template <utils::concepts::StringLike Name, utils::concepts::StringLike Surname>
	[[nodiscard]] Employee::Employee(Name&& name, Surname&& surname, const unsigned short age, const unsigned int salary) noexcept
		: m_name{ std::forward<Name>(name) }, m_surname{ std::forward<Name>(surname) }, m_age{ age }, m_salary{ salary }, m_valid{ true } {}

	template<utils::concepts::StringLike Name>
	[[maybe_unused]] auto Employee::setName(Name&& name) noexcept -> Employee&
	{
		m_name = std::forward<Name>(name);
		return *this;
	}

	template<utils::concepts::StringLike Surname>
	[[maybe_unused]] auto Employee::setSurname(Surname&& surname) noexcept -> Employee&
	{
		m_surname = std::forward<Surname>(surname);
		return *this;
	}

#pragma endregion Implementation

}

