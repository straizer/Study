#include "JippVector.hpp"

namespace jipp
{
	namespace _internal
	{
		auto _throwOnFailedPredicate(const bool predicate, const std::string& message) -> void
		{
			begin_save_except
				if (!predicate) [[unlikely]]
					throw std::invalid_argument(message);
			end_save_except
		}
	}
}
