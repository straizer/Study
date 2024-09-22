#include "CppUnitTest.h"

#include "IO.hpp"

using namespace Microsoft::VisualStudio::CppUnitTestFramework;
#include <iostream>
namespace UtilsTest
{
	TEST_CLASS(IOTest)
	{
	public:

		TEST_METHOD(TestMethod)
		{
			Assert::AreEqual(3, 3);
			std::cout.exceptions(std::ios_base::failbit);
			std::cout.setstate(std::ios_base::failbit);
			Logger::WriteMessage(std::to_string(std::cout.exceptions()).c_str());
		}
	};
}
