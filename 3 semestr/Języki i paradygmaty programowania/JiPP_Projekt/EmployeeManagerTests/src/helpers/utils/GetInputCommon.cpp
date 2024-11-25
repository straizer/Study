#include "utils-test/GetInputCommon.hpp"

namespace UtilsReadStreamTest
{
	[[nodiscard]] auto getStream(const std::string& value, const std::ios_base::iostate state) -> std::istringstream
	{
		std::istringstream stream{ value };
		stream.setstate(state);
		return stream;
	}

	[[nodiscard]] auto getTiedStream(std::istringstream& stream, const std::ios_base::iostate state) -> std::ostringstream
	{
		std::ostringstream tiedStream;
		tiedStream.setstate(state);
		stream.tie(&tiedStream);
		return tiedStream;
	}
}