#pragma once

#include <functional>
#include <iostream>
#include <optional>
#include <string>

#include "utils/Finally.hpp"

/**
 * @brief Namespace containing common utils.
 */
namespace utils
{
	/**
	 * @brief Namespace containing IO helpers.
	 */
	namespace IO
	{
		/**
		 * @brief Internal namespace, NOT FOR EXTERNAL USE!
		 */
		namespace _internal
		{
			/**
			 * @brief Wrong input message displayed if extracting cause setting std::ios::failbit or validation of extracted value failed.
			 */
			const std::string wrongInputMessage = "Wrong input. Try again: ";

			/**
			 * @brief Sets exception mask on stream.
			 * @param stream - stream to modify
			 * @param includeEofBit - include std::ios::eofbit in exception mask
			 * @return Old exception mask
			 * @exception std::ios_base::failure - if stream has std::ios::failbit or std::ios::badbit already set.
			 * @exception std::ios_base::failure - if tied stream has std::ios::failbit, std::ios::badbit or std::ios::eofbit already set.
			 */
			[[nodiscard]] auto _setExceptionMask(std::basic_ios<char>* const stream, const bool includeEofBit) -> std::ios::iostate;

			/**
			 * @brief Gets output stream tied to stream and inserts value to it.
			 * @param stream - input stream that possibly have tied output stream
			 * @param value - value to insert to output stream
			 * @exception std::ios_base::failure - if inserting to output stream sets std::ios::badbit on it.
			 */
			auto _insertToTiedStream(std::istream& stream, const std::string& value) -> void;

			/**
			 * @brief Extracts T from stream.
			 * @tparam T - type of value to extract
			 * @param stream - stream to extract value from
			 * @return Extracted value or std::nullopt if EOF is encoutered.
			 * @exception std::ios_base::failure - if extracting from stream or inserting to output stream sets std::ios::badbit on it.
			 */
			template<class T>
			[[nodiscard]] auto _extractFromStream(std::istream& stream) -> std::optional<T>;
		}

		/**
		 * @brief Reads T from given stream. If whitespace is encountered, and read value is of invalid type, ignores all characters in
		 * currently read line, and tries to read value from next line. If T is StringLike, reads whole line up to "\n" character instead.
		 * @tparam T - type of input to read
		 * @param prompt - optional prompt to output stream; default is empty
		 * @param validator - optional validator for read value
		 * @param stream - optional input stream; default is std::cin
		 * @return Read value or std::nullopt if EOF is encoutered.
		 * @exception std::ios_base::failure - if stream has std::ios::failbit or std::ios::badbit set before any operation.
		 * @exception std::ios_base::failure - if tied stream has std::ios::failbit, std::ios::badbit or std::ios::eofbit set before any operation.
		 * @exception std::ios_base::failure - if extracting from stream or inserting to output stream sets std::ios::badbit on it.
		 */
		template <class T>
		[[nodiscard]] auto readStream(
			const std::string& prompt = "",
			const std::function<bool(const T&)>& validator = [](const T&) { return true; },
			std::istream& stream = std::cin
		) -> std::optional<T>;

		/**
		 * @brief Writes value to given stream.
		 * @param value - value to write; must fulfill RepresentableAsString constraint
		 * @param stream - optional output stream; default is std::cout
		 * @return Given output stream.
		 * @exception std::ios_base::failure - if stream has std::ios::failbit, std::ios::badbit or std::ios::eofbit set before any operation.
		 * @exception std::ios_base::failure - if inserting to stream sets std::ios::badbit on it.
		 */
		auto writeStream(const concepts::RepresentableAsString auto& value, std::ostream& stream = std::cout) -> std::ostream&;

		//TODO: Tests
		template<typename T>
		void writeBinaryStream(std::ostream& stream, const T& data, size_t elementCount = 1)
		{
			stream.write(std::bit_cast<char*>(&data), static_cast<std::streamsize>(sizeof(data) * elementCount));
		}

		//TODO: Tests
		template<typename T>
		void readBinaryStream(std::istream& stream, T& data, size_t elementCount = 1)
		{
			stream.read(std::bit_cast<char*>(&data), static_cast<std::streamsize>(sizeof(data) * elementCount));
		}



		/**
		 * @brief Namespace related to console operations.
		 */
		namespace console
		{
			/**
			 * @brief Gets single pressed key.
			 * @return Tuple, where first element is ASCII code of pressed key, second is 0 unless function key was pressed.
			 */
			auto getPressedKey(void) noexcept -> std::tuple<int, int>;

			/**
			 * @brief Clears the console.
			 */
			auto clear(void) noexcept -> void;
		}

#pragma region Implementation

		template<class T>
		[[nodiscard]] auto readStream(const std::string& prompt, const std::function<bool(const T&)>& validator, std::istream& stream) -> std::optional<T>
		{
			if (stream.rdstate() & std::ios::eofbit)
				return std::nullopt;
			std::ios::iostate mask = _internal::_setExceptionMask(&stream, false);
			auto maskRestorer = finally({ stream.exceptions(mask); });
			_internal::_insertToTiedStream(stream, prompt);
			while (true)
			{
				std::optional<T> input = _internal::_extractFromStream<T>(stream);
				if (!input.has_value() || validator(input.value()))
					return input;
				_internal::_insertToTiedStream(stream, _internal::wrongInputMessage);
			}
		}

		auto writeStream(const concepts::RepresentableAsString auto& value, std::ostream& stream) -> std::ostream&
		{
			std::ios::iostate mask = _internal::_setExceptionMask(&stream, true);
			auto maskRestorer = finally({ stream.exceptions(mask); });
			try
			{
				stream << value;
			}
			catch (const std::ios_base::failure&)
			{
				if (stream.rdstate() & std::ios::badbit)
					throw;
				stream.clear();
			}
			return stream;
		}

#pragma region Internal

		namespace _internal
		{
			template<class T>
			[[nodiscard]] auto _extractFromStream(std::istream& stream) -> std::optional<T>
			{
				T input{};
				while (true)
				{
					try
					{
						if constexpr (concepts::StringLike<T>)
							std::getline(stream >> std::ws, input);
						else
							stream >> input;
						return input;
					}
					catch (const std::ios_base::failure&)
					{
						std::ios::iostate streamState = stream.rdstate();
						if (streamState & std::ios::badbit)
							throw;
						if (streamState & std::ios::eofbit)
							return std::nullopt;
						stream.clear();
						stream.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
						_internal::_insertToTiedStream(stream, wrongInputMessage);
					}
				}
			}
		}

#pragma endregion Internal

#pragma endregion Implementation

	}
}
