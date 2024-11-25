#pragma once

#include <filesystem>

#include "Employee.hpp"
#include "JippVector.hpp"

/**
 * @brief Namespace containing final project for JiPP2 subject.
 */
namespace jipp
{
	/**
	 * @brief Employee Manager for handling employees.
	 */
	class EmployeeManager final
	{
	public:

		/**
		 * @brief Runs main loop of the program. Never returns to caller.
		 */
		[[noreturn]] static auto run(void) -> void;

	private:

		/**
		 * @brief Container with all Employees.
		 */
		JippVector<Employee> employees;

		/**
		 * @brief Menu displayed to user.
		 */
		std::string m_menu;

		/**
		 * @brief Path holding file with menu text.
		 */
		const std::filesystem::path m_menuPath{ "resources/menu.txt" };


		/**
		 * @brief Default constructor. Constructs the EmployeeManager object.
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 */
		[[nodiscard]] EmployeeManager(void);


		/**
		 * @brief Loads menu text to m_menu. If file specified in m_menuPath does not exist, terminates the program.
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 */
		auto loadMenu(void) -> void;

		/**
		 * @brief Prints menu and current number of Employees.
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 */
		auto printMenu(void) -> void;

		/**
		 * @brief Gets key pressed by user.
		 * @return Pressed digit.
		 */
		auto getPressedKey(void) noexcept -> int;

		/**
		 * @brief Adds Employee.
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 * @exception std::runtime_error - if reallocation of container fails.
		 */
		auto addEmployee(void) -> void;

		/**
		 * @brief Inserts Employee.
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 * @exception std::runtime_error - if reallocation of container fails.
		 */
		auto insertEmployee(void) -> void;

		/**
		 * @brief Modify Employee.
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 */
		auto modifyEmployee(void) -> void;

		/**
		 * @brief Remove Employee.
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 * @exception std::runtime_error - if reallocation of container fails.
		 */
		auto removeEmployee(void) -> void;

		/**
		 * @brief Prints Employees.
		 * @param printExtraText - should print header and footer
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 */
		auto printEmployees(bool printExtraText = true) -> void;

		/**
		 * @brief Prints Employees in range.
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 */
		auto printEmployeesInRange(void) -> void;

		auto processEmployees(void) -> void;

		/**
		 * @brief Save Employees.
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 */
		auto saveEmployees(void) const -> void;

		/**
		 * @brief Load Employees.
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 * @exception std::runtime_error - if reallocation of container fails.
		 */
		auto loadEmployees(void) -> void;

		/**
		 * @brief Waits for any key pressed.
		 * @exception std::ios_base::failure - if I/O operation sets std::ios::badbit on stream.
		 */
		auto waitForContinue(void) -> void;

		auto searchEmployees(void) -> void;

		/**
		 * @brief Deleted copy constructor.
		 */
		EmployeeManager(const EmployeeManager&) = delete;

		/**
		 * @brief Deleted copy assignment operator.
		 */
		void operator=(const EmployeeManager&) = delete;
	};
}

