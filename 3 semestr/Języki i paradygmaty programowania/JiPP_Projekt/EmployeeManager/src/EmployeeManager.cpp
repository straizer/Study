#include "EmployeeManager.hpp"

#include <algorithm>
#include <fstream>

#include "utils/IO.hpp"
#include "utils/StringUtils.hpp"

namespace jipp
{
	[[noreturn]] auto EmployeeManager::run(void) -> void
	{
		EmployeeManager manager;

		while (true)
		{
			manager.printMenu();

			switch (manager.getPressedKey())
			{
			case 1:
				manager.addEmployee();
				break;
			case 2:
				manager.insertEmployee();
				break;
			case 3:
				manager.modifyEmployee();
				break;
			case 4:
				manager.removeEmployee();
				break;
			case 5:
				manager.printEmployees();
				break;
			case 6:
				manager.printEmployeesInRange();
				break;
			case 7:
				manager.processEmployees();
				break;
			case 8:
				manager.saveEmployees();
				break;
			case 9:
				manager.loadEmployees();
				break;
			case 0:
				std::exit(EXIT_SUCCESS);
			}

			manager.waitForContinue();
		}
	}

	EmployeeManager::EmployeeManager(void)
	{
		loadMenu();
	}

	auto EmployeeManager::loadMenu(void) -> void
	{
		if (!std::filesystem::exists(m_menuPath))
		{
			std::string message = utils::string::concatenate()("Unable to open file ", m_menuPath, ". Press any key to terminate.");
			utils::IO::writeStream(message);
			utils::IO::console::getPressedKey();
			std::exit(EXIT_FAILURE);
		}
		std::ifstream file{ m_menuPath };
		auto dummyValidator = [](const std::string&) { return true; };
		while (true)
		{
			std::optional<std::string> line = utils::IO::readStream<std::string>("", dummyValidator, file);
			if (!line.has_value())
				break;
			m_menu = utils::string::concatenate("\n")(m_menu, line.value());
		}
	}

	auto EmployeeManager::printMenu(void) -> void
	{
		utils::IO::console::clear();
		std::string info = utils::string::concatenate(" ")("Current number of employees:", employees.size(), '\n');
		utils::IO::writeStream(utils::string::concatenate("\n")(m_menu, info));
	}

	auto EmployeeManager::getPressedKey(void) noexcept -> int
	{
		while (true)
		{
			auto [pressedASCII, _] = utils::IO::console::getPressedKey();
			int pressed = pressedASCII - '0';
			if (pressed >= 0 && pressed <= 9)
				return pressed;
		}
	}

	auto EmployeeManager::addEmployee(void) -> void
	{
		utils::IO::writeStream("\nADDING EMPLOYEE\n");
		std::string name = utils::IO::readStream<std::string>("Enter name: ").value();
		std::string surname = utils::IO::readStream<std::string>("Enter surname: ").value();
		unsigned short age = utils::IO::readStream<unsigned short>("Enter age between 1 and 120: ", [](const unsigned short& _age) { return _age >= 1 && _age <= 120; }).value();
		unsigned int salary = utils::IO::readStream<unsigned int>("Enter salary between 1 and 1'000'000: ", [](const unsigned int& _salary) { return _salary >= 1 && _salary <= 1000000; }).value();
		employees.pushBack({ name, surname, age, salary });
		utils::IO::writeStream("Employee added. ");
	}

	auto EmployeeManager::insertEmployee(void) -> void
	{
		utils::IO::writeStream("\nINSERTING EMPLOYEE\n");
		printEmployees(false);
		int index = utils::IO::readStream<int>("Enter index to insert employee: ", [&](const int& choice) { return choice >= 1 && choice <= employees.size() + 1; }).value() - 1;
		std::string name = utils::IO::readStream<std::string>("Enter name: ").value();
		std::string surname = utils::IO::readStream<std::string>("Enter surname: ").value();
		unsigned short age = utils::IO::readStream<unsigned short>("Enter age between 1 and 120: ", [](const unsigned short& _age) { return _age >= 1 && _age <= 120; }).value();
		unsigned int salary = utils::IO::readStream<unsigned int>("Enter salary between 1 and 1'000'000: ", [](const unsigned int& _salary) { return _salary >= 1 && _salary <= 1000000; }).value();
		employees.insert(index, { name, surname, age, salary });
		utils::IO::writeStream("Employee inserted. ");
	}

	auto EmployeeManager::modifyEmployee(void) -> void
	{
		utils::IO::writeStream("\nMODIFYING EMPLOYEE\n");
		printEmployees(false);
		int index = utils::IO::readStream<int>("Enter employee no. to modify: ", [&](const int& choice) { return choice >= 1 && choice <= employees.size(); }).value() - 1;

		while (true)
		{
			printMenu();
			utils::IO::writeStream("\nMODIFYING EMPLOYEE\n");
			utils::IO::writeStream(employees[index]);
			utils::IO::writeStream("\nWhat to modify?\n");
			utils::IO::writeStream("1. Name\n");
			utils::IO::writeStream("2. Surname\n");
			utils::IO::writeStream("3. Age\n");
			utils::IO::writeStream("4. Salary\n");
			utils::IO::writeStream("\n0. Finish modifying\n");

			bool doContinue = true;
			switch (getPressedKey())
			{
			case 1:
			{
				std::string name = utils::IO::readStream<std::string>("Enter new name: ").value();
				employees[index].setName(name);
				break;
			}
			case 2:
			{
				std::string surname = utils::IO::readStream<std::string>("Enter new surname: ").value();
				employees[index].setSurname(surname);
				break;
			}
			case 3:
			{
				unsigned short age = utils::IO::readStream<unsigned short>("Enter new age between 1 and 120: ", [](const unsigned short& _age) { return _age >= 1 && _age <= 120; }).value();
				employees[index].setAge(age);
				break;
			}
			case 4:
			{
				unsigned int salary = utils::IO::readStream<unsigned int>("Enter new salary between 1 and 1'000'000: ", [](const unsigned int& _salary) { return _salary >= 1 && _salary <= 1000000; }).value();
				employees[index].setSalary(salary);
				break;
			}
			case 0:
				doContinue = false;
			}
			if (!doContinue)
				break;
			utils::IO::writeStream("Employee modified.\n");
		}
		utils::IO::writeStream("Modifying finished. ");
	}

	auto EmployeeManager::removeEmployee(void) -> void
	{
		utils::IO::writeStream("\nREMOVING EMPLOYEE\n");
		printEmployees(false);
		int index = utils::IO::readStream<int>("Enter employee no. to delete: ", [&](const int& choice) { return choice >= 1 && choice <= employees.size(); }).value() - 1;
		employees.erase(index);
		utils::IO::writeStream("Removing finished. ");
	}

	auto EmployeeManager::printEmployees(bool printExtraText) -> void
	{
		if (printExtraText)
			utils::IO::writeStream("\nPRINTING EMPLOYEES\n");
		std::ranges::for_each(employees | std::views::enumerate, [](const auto& indexEmployeePair) { 
			const auto& [index, employee] = indexEmployeePair;			
			utils::IO::writeStream(utils::string::concatenate(" | ")(index + 1, employee));
		});
		if (printExtraText)
			utils::IO::writeStream("Printing finished. ");
	}

	auto EmployeeManager::printEmployeesInRange(void) -> void
	{
		utils::IO::writeStream("\nPRINTING EMPLOYEES\n");
		int firstIndex = utils::IO::readStream<int>("Enter first index to print: ", [&](const int& choice) { return choice >= 1 && choice <= employees.size(); }).value() - 1;
		int lastIndex = utils::IO::readStream<int>("Enter last index to print: ", [&](const int& choice) { return choice > firstIndex && choice <= employees.size(); }).value() - 1;
		std::ranges::for_each(
			employees | std::views::enumerate | std::views::drop(firstIndex) | std::views::take(lastIndex - firstIndex + 1),
			[](const auto& indexEmployeePair) {
				const auto& [index, employee] = indexEmployeePair;
				utils::IO::writeStream(utils::string::concatenate(" | ")(index + 1, employee));
			}
		);
		utils::IO::writeStream("Printing finished. ");
	}

	auto EmployeeManager::processEmployees(void) -> void
	{
		while (true)
		{
			printMenu();
			utils::IO::writeStream("\nPROCESSING EMPLOYEES\n");
			utils::IO::writeStream("\nWhat to process?\n");
			utils::IO::writeStream("1. Search\n");
			utils::IO::writeStream("2. Average salary\n");
			utils::IO::writeStream("3. Highest salary\n");
			utils::IO::writeStream("\n0. Finish processing\n");

			bool doContinue = true;
			switch (getPressedKey())
			{
			case 1:
				searchEmployees();
				break;
			case 2:
				utils::IO::writeStream(utils::string::concatenate()("Average salary: ",
					std::ranges::fold_left(
						employees | std::views::transform([](const Employee& employee) {
							return employee.getSalary();
						}), 0, std::plus<>()) / employees.size(), "\n"));
				break;
			case 3:
			{
				if (employees.isEmpty())
				{
					utils::IO::writeStream("No employees. ");
					break;
				}
				int highestSalaryEmployeeId = 0;
				for (int i = 1; i < employees.size(); i++)
					if (employees[i].getSalary() > employees[highestSalaryEmployeeId].getSalary())
						highestSalaryEmployeeId = i;	
				utils::IO::writeStream(utils::string::concatenate()("Highest salary:\n", employees[highestSalaryEmployeeId]));
				break;
			}
			case 0:
				doContinue = false;
			}
			if (!doContinue)
				break;
			waitForContinue();
		}
		utils::IO::writeStream("Processing finished. ");
	}

	auto EmployeeManager::saveEmployees(void) const -> void
	{
		utils::IO::writeStream("\nSAVING EMPLOYEES\n");
		std::optional<std::string> fileName = utils::IO::readStream<std::string>("Enter filename: ", [](const std::string& path) { return !path.contains(' '); });
		std::ofstream file{ fileName.value() };
		utils::IO::writeBinaryStream(file, employees.size());
		std::ranges::for_each(employees, [&](const Employee& employee) {
			employee.writeBinary(file);
		});
		utils::IO::writeStream("Saving finished. ");
	}

	auto EmployeeManager::loadEmployees(void) -> void
	{
		utils::IO::writeStream("\nLOADING EMPLOYEES\n");
		std::optional<std::string> fileName = utils::IO::readStream<std::string>("Enter filename: ", [](const std::string& path) { return std::filesystem::exists(path); });
		std::ifstream file{ fileName.value() };
		int size;
		utils::IO::readBinaryStream(file, size);
		employees.clear();
		std::ranges::for_each(std::views::iota(0, size), [&](const int index) {
			employees.pushBack(Employee());
			employees[index].readBinary(file);
		});
		utils::IO::writeStream("Loading finished. ");
	}

	auto EmployeeManager::waitForContinue(void) -> void
	{
		utils::IO::writeStream("Press any key to continue...");
		utils::IO::console::getPressedKey();
	}
	
	auto EmployeeManager::searchEmployees(void) -> void
	{
		utils::IO::writeStream("\nSEARCHING EMPLOYEE\n");
		utils::IO::writeStream("\nWhat to search?\n");
		utils::IO::writeStream("1. Name\n");
		utils::IO::writeStream("2. Surname\n");
		utils::IO::writeStream("3. Age\n");
		utils::IO::writeStream("4. Age range\n");

		switch (getPressedKey())
		{
		case 1:
		{
			std::string name = utils::IO::readStream<std::string>("Enter name: ").value();
			std::ranges::for_each(
				employees | std::views::filter([&](const Employee& employee) {
					return employee.getName() == name; }),
				[](const Employee& employee) { utils::IO::writeStream(employee); });
			break;
		}
		case 2:
		{
			std::string surname = utils::IO::readStream<std::string>("Enter surname: ").value();
			std::ranges::for_each(
				employees | std::views::filter([&](const Employee& employee) {
					return employee.getSurname() == surname; }),
				[](const Employee& employee) { utils::IO::writeStream(employee); });
			break;
		}
		case 3:
		{
			unsigned short age = utils::IO::readStream<unsigned short>("Enter age between 1 and 120: ", [](const unsigned short& _age) { return _age >= 1 && _age <= 120; }).value();
			std::ranges::for_each(
				employees | std::views::filter([&](const Employee& employee) {
					return employee.getAge() == age; }),
				[](const Employee& employee) { utils::IO::writeStream(employee); });
			break;
		}
		case 4:
		{
			unsigned short lowerAge = utils::IO::readStream<unsigned short>("Enter age between 1 and 120: ", [](const unsigned short& _age) { return _age >= 1 && _age <= 120; }).value();
			unsigned short upperAge = utils::IO::readStream<unsigned short>(std::string("Enter age between ") + std::to_string(lowerAge) + " and 120: ", [&](const unsigned short& _age) { return _age >= lowerAge && _age <= 120; }).value();
			std::ranges::for_each(
				employees | std::views::filter([&](const Employee& employee) {
					return employee.getAge() >= lowerAge && employee.getAge() <= upperAge; }),
				[](const Employee& employee) { utils::IO::writeStream(employee); });
			break;
		}
		}
	}
}
