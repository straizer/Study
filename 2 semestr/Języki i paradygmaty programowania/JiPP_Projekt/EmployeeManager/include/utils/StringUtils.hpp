#pragma once

#include <sstream>

#include "Concepts.hpp"

/**
 * @brief Namespace containing common utils.
 */
namespace utils
{
	/**
	 * @brief Namespace containing string helpers.
	 */
	namespace string
	{
		/**
		 * @brief Encapsulates concatenating of diffrent types to std::string.
		 * Called by: concatenate(<separator>)(<values to concatenate>).
		 */
		class concatenate final
		{
		public:

			/**
			 * @brief Creates concatenating class.
			 * @tparam T - type of separator; must fulfill StringLike constraint; default std::string
			 * @param separator - separator between concatenated items; default empty std::string
			 */
			template <concepts::StringLike T = std::string>
			[[nodiscard]] explicit concatenate(T&& separator = "") noexcept;


			/**
			 * @brief Concatenates RepresentableAsString values.
			 * @param first - first element to concatenate
			 * @param ...rest - rest of the elements to concatenate
			 * @return Concatenated values as std::string.
			 */
			[[nodiscard]] auto operator()(
				const concepts::RepresentableAsString auto& first,
				const concepts::RepresentableAsString auto&... rest
			) noexcept -> std::string;

			/**
			 * @brief Converts RepresentableAsString value to std::string.
			 * @param value - value to convert
			 * @return String representation of value.
			 */
			[[nodiscard]] auto operator()(const concepts::RepresentableAsString auto& value) noexcept -> std::string;

		private:

			/**
			 * @brief Separator to insert between values.
			 */
			std::string m_separator;

			/**
			 * @brief Internal stream to insert values to.
			 */
			std::ostringstream m_stream{};


			/**
			 * @brief Deleted copy constructor.
			 */
			concatenate(const concatenate&) = delete;

			/**
			 * @brief Deleted copy assignment operator.
			 */
			void operator=(const concatenate&) = delete;
		};

#pragma region Implementation
		
		template <concepts::StringLike T>
		[[nodiscard]] concatenate::concatenate(T&& separator) noexcept
			: m_separator{ std::forward<T>(separator) } {}

		[[nodiscard]] auto concatenate::operator()(const concepts::RepresentableAsString auto& first, const concepts::RepresentableAsString auto&... rest) noexcept -> std::string
		{
			m_stream << first << m_separator;
			return this->operator()(rest...);
		}

		[[nodiscard]] auto concatenate::operator()(const concepts::RepresentableAsString auto& value) noexcept -> std::string
		{
			m_stream << value;
			return m_stream.str();
		}

#pragma endregion Implementation

	}
}
