#pragma once

#include <sstream>
#include <string_view>
#include <type_traits>

/**
 * @brief Namespace containing common utils.
 */
namespace utils
{
	/**
	 * @brief Namespace containing custom concepts.
	 */
	namespace concepts
	{
		/**
		 * @brief Concept requiring that T is Invocable.
		 */
		template <class T> concept Invocable = std::is_invocable_v<T>;

		/**
		 * @brief Concept requiring that T is noexcept Invocable.
		 */
		template <class T> concept NoexceptInvocable = Invocable<T> && requires(T a)
		{
			requires noexcept(std::invoke(a));
		};

		/**
		 * @brief Concept requiring that T is convertible to std::string.
		 */
		template <class T> concept StringLike = std::is_convertible_v<T, std::string_view>;

		/**
		 * @brief Concept requiring that T is representable as std::string.
		 */
		template <class T> concept RepresentableAsString = requires(T a, std::stringstream stream)
		{
			stream << a;
		};
	}
}
