#pragma once

#include "Common.hpp"

#include "utils/IO.hpp"
#include "utils/StringUtils.hpp"

namespace UtilsReadStreamTest
{
	const std::string whitespaces = " \t\r\n";

	[[nodiscard]] auto getStream(const std::string& value = "", const std::ios_base::iostate state = std::ios::goodbit) -> std::istringstream;

	[[nodiscard]] auto getTiedStream(std::istringstream& stream, const std::ios_base::iostate state = std::ios::goodbit) -> std::ostringstream;

}