#include "utils/IO.hpp"

#include <conio.h>

namespace utils
{
	namespace IO
	{
		namespace _internal
		{
			[[nodiscard]] auto _setExceptionMask(std::basic_ios<char>* const stream, const bool includeEofBit) -> std::ios::iostate
			{
				if (!stream)
					return std::ios::goodbit;
				const std::ios::iostate exceptionMask = stream->exceptions();
				stream->exceptions(std::ios::failbit | std::ios::badbit | (includeEofBit ? std::ios::eofbit : std::ios::goodbit));
				return exceptionMask;
			}

			auto _insertToTiedStream(std::istream& stream, const std::string& value) -> void
			{
				if (stream.tie())
					writeStream(value, *stream.tie());
			}
		}

		namespace console
		{
			auto getPressedKey(void) noexcept -> std::tuple<int, int>
			{
				const int firstValue = _getch();
				int secondValue = (firstValue == 0x00 || firstValue == 0xE0) ? _getch() : 0;
				return { firstValue, secondValue };
			}

			auto clear(void) noexcept -> void
			{
				std::cout << "\x1B[2J\x1B[H";
			}
		}
	}
}
