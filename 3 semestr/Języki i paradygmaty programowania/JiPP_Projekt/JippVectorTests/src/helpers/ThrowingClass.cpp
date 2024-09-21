#include "ThrowingClass.hpp"

#include <exception>

ThrowingClass::ThrowingClass(void)
{
	throw std::exception("Test message");
}
