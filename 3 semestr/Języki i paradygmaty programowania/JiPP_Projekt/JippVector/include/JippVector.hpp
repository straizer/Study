#pragma once

#include <ranges>
#include <sstream>
#include <stacktrace>

/**
 * @brief Macro opening block that could throw.
 */
#define begin_save_except jipp::_internal::_safeExcept([&](){

 /**
  * @brief Macro closing block that could throw.
  */
#define end_save_except });

/**
 * @brief Namespace containing final project for JiPP2 subject.
 */
namespace jipp
{	
	/**
	 * @brief Namespace containing concepts used in "jipp" namespace
	 */
	namespace concepts
	{
		/**
		 * @brief Concept requiring that T is CopyAssignable.
		 */
		template <class T> concept CopyAssignable = std::is_copy_assignable_v<T>;

		/**
		 * @brief Concept requiring that T is CopyConstructible.
		 */
		template <class T> concept CopyConstructible = std::copy_constructible<T>;

		/**
		 * @brief Concept requiring that T is Erasable.
		 */
		template <class T> concept Erasable = requires(T * a) { std::destroy_at(a); };

		/**
		 * @brief Concept requiring that T is Invocable.
		 */
		template <class T> concept Invocable = std::is_invocable_v<T>;

		/**
		 * @brief Concept requiring that T is CopyAssignable, CopyConstructible and Erasable.
		 */
		template <class T> concept Storable = CopyAssignable<T> && CopyConstructible<T> && Erasable<T>;
	}

	/**
	 * @brief Forward declaration of JippVector class.
	 */
	template <concepts::Storable T>
	class JippVector;

	/**
	 * @brief Internal namespace, NOT FOR EXTERNAL USE!
	 */
	namespace _internal
	{
		/**
		 * @brief Extracts internal buffer of JippVector, used only for testing.
		 * @tparam T - type of data in JippVector; must fulfill Storable constraint
		 * @param vector - JippVector to extract internal buffer from
		 * @return Extracted internal buffer as raw pointer to T.
		 */
		template <concepts::Storable T>
		[[nodiscard]] auto _extract(const JippVector<T>& vector) noexcept -> T*;

		/**
		 * @brief Runs given "functor" and return result of it.
		 * If "functor" throws, catches exception, wraps it in "std::runtime_error" with additional info and throws "std::runtime_error".
		 * @param functor - functor to run; must fulfill Invocable constraint
		 * @exception std::runtime_error - if "functor" throws any exception of class "std::exception" or its subclasses.
		 */
		auto _safeExcept(const concepts::Invocable auto& functor) -> decltype(std::invoke(functor));

		/**
		 * @brief Checks if predicate is fulfilled and throws std::invalid_argument if not.
		 * @param predicate - boolean value to verify
		 * @param message - error message
		 * @exception std::invalid_argument - if "predicate" is not fulfilled.
		 */
		auto _throwOnFailedPredicate(const bool predicate, const std::string& message) -> void;
	}

	/**
	 * @brief Sequence container that encapsulates dynamic size arrays.
	 * @tparam T - type of data in container; must fulfill Storable constraint
	 */
	template <concepts::Storable T>
	class JippVector
	{
	public:

		/**
		 * @brief Default constructor. Constructs an empty container.
		 * @exception std::runtime_error - if allocation of internal buffer fails
		 * or any exception is thrown by the constructor of T; if thrown, this constructor has no effect.
		 */
		[[nodiscard]] JippVector(void);

		/**
		 * @brief Constructs the container with with space allocated for "capacity" elements.
		 * @param capacity - the capacity of the container
		 * @exception std::runtime_error - if "capacity" is negative or zero, allocation of internal buffer fails
		 * or any exception is thrown by the constructor of T; if thrown, this constructor has no effect.
		 */
		[[nodiscard]] explicit JippVector(const int capacity);

		/**
		 * @brief Constructs the container with the contents of the initializer list "data".
		 * @param data - initializer list to initialize the elements of the container with; may be empty
		 * @exception std::runtime_error - if allocation of internal buffer fails
		 * or any exception is thrown by the constructor of T; if thrown, this constructor has no effect.
		 */
		[[nodiscard]] JippVector(const std::initializer_list<T> data);

		/**
		 * @brief Copy constructor. Constructs the container with the copy of the contents of "other".
		 * @param other - another container to be used as source to initialize the elements of the container with
		 * @exception std::runtime_error - if allocation of internal buffer fails
		 * or any exception is thrown by the constructor of T; if thrown, this constructor has no effect.
		 */
		[[nodiscard]] JippVector(const JippVector& other);

		/**
		 * @brief Constructs the container with the contents of the "range".
		 * @param range - a container compatible range, that is, an input_range whose elements are convertible to T
		 * @exception std::runtime_error - if allocation of internal buffer fails
		 * or any exception is thrown by the constructor of T; if thrown, this constructor has no effect.
		 */
		[[nodiscard]] JippVector(std::ranges::input_range auto&& range);

		/**
		 * @brief Destructs the container.
		 * The destructors of the elements are called and the used storage is deallocated.
		 * Note, that if the elements are pointers, the pointed-to objects are not destroyed. 
		 */
		virtual ~JippVector(void) noexcept = default;


		/**
		 * @brief Returns the number of elements that the container has currently allocated space for.
		 * @return Capacity of the currently allocated storage.
		 */
		[[nodiscard]] auto capacity(void) const noexcept -> int;

		/**
		 * @brief Returns the number of elements in the container.
		 * @return The number of elements in the container.
		 */
		[[nodiscard]] auto size(void) const noexcept -> int;

		/**
		 * @brief Checks if the container has no elements.
		 * @return true if the container is empty, false otherwise.
		 */
		[[nodiscard]] auto isEmpty(void) const noexcept -> bool;

		/**
		 * @brief Returns a pointer to the const first element of the container.
		 * If the container is empty, the returned pointer will be equal to "nullptr". 
		 * @return Pointer to the const first element. 
		 */
		[[nodiscard]] auto begin(void) const noexcept -> const T*;

		/**
		 * @brief Returns a pointer to the first element of the container.
		 * If the container is empty, the returned pointer will be equal to "nullptr".
		 * @return Pointer to the first element.
		 */
		[[nodiscard]] auto begin(void) noexcept -> T*;

		/**
		 * @brief Returns a pointer to the const element following the last element of the container.
		 * This element acts as a placeholder; attempting to access it results in undefined behavior.
		 * If the container is empty, the returned pointer will be equal to "nullptr".
		 * @return Pointer to the const element following the last element.
		 */
		[[nodiscard]] auto end(void) const noexcept -> const T*;

		/**
		 * @brief Returns a pointer to the element following the last element of the container.
		 * This element acts as a placeholder; attempting to access it results in undefined behavior.
		 * If the container is empty, the returned pointer will be equal to "nullptr".
		 * @return Pointer to the element following the last element.
		 */
		[[nodiscard]] auto end(void) noexcept -> T*;

		/**
		 * @brief Assigns the given "value" to all elements.
		 * @param value - the value to be assigned
		 * @exception std::runtime_error - if any exception is thrown by the constructor of T;
		 * if thrown, this method has no effect.
		 */
		auto fill(const T& value) -> void;

		/**
		 * @brief Assigns the given "value" to the elements in the range [start, start+count).
		 * @param start - index of first element to modify
		 * @param count - number of elements to modify
		 * @param value - the value to be assigned
		 * @exception std::runtime_error - if "start" and "count" pair is invalid
		 * or any exception is thrown by the constructor of T; if thrown, this method has no effect.
		 */
		auto fill(const int start, const int count, const T& value) -> void;

		/**
		 * @brief Increase the capacity of the container to a value that's equal to "newCapacity";
		 * if "newCapacity" is lesser or equal to the current capacity(), this method does nothing. 
		 * @param newCapacity - new capacity of the container, in number of elements
		 * @exception std::runtime_error - if "newCapacity" is less than size,
		 * or allocation of internal buffer fails or any exception is thrown by the constructor of T.
		 */
		auto reserve(const int newCapacity) -> void;

		/**
		 * @brief Appends the given element value to the end of the container.
		 * @param value the value of the element to append
		 * @exception std::runtime_error - if allocation of internal buffer fails
		 * or any exception is thrown by the constructor of T.
		 */
		auto pushBack(const T& value) -> void;

		/**
		 * @brief Appends the given element value to the front of the container.
		 * @param value the value of the element to append
		 * @exception std::runtime_error - if allocation of internal buffer fails
		 * or any exception is thrown by the constructor of T.
		 */
		auto pushFront(const T& value) -> void;

		/**
		 * @brief Inserts element at the specified location in the container.
		 * @param index - index before which the content will be inserted (may be end())
		 * @param value - element value to insert
		 * @exception std::runtime_error - if if given "index" is greater than container size, or less than 0,
		 * or allocation of internal buffer fails or any exception is thrown by the constructor of T.
		 */
		auto insert(const int index, const T& value) -> void;

		/**
		 * @brief Erases the specified elements from the container. 
		 * @param start - index of first element to erase
		 * @param count - number of elements to erase
		 * @exception std::runtime_error - if "start" and "count" pair is invalid, or allocation of internal
		 * buffer fails, or any exception is thrown by the constructor of T.
		 */
		auto erase(const int start, const int count) -> void;

		/**
		 * @brief Erases the specified element from the container.
		 * @param index - index of element to erase
		 * @exception std::runtime_error - if "index" is greater or equal to size or less than zero, or allocation
		 * of internal buffer fails, or any exception is thrown by the constructor of T.
		 */
		auto erase(const int index) -> void;

		/**
		 * @brief Requests the removal of unused capacity.
		 * @exception std::runtime_error - if allocation of internal buffer fails
		 * or any exception is thrown by the constructor of T.
		 */
		auto shrinkToFit(void) -> void;

		/**
		 * @brief Erases all elements from the container. After this call, size() returns zero.
		 * Leaves the capacity() of the container unchanged.
		 */
		auto clear(void) noexcept -> void;


		/**
		 * @brief Returns a const reference to the element at specified location "index".
		 * @param index - position of the element to return 
		 * @return Const reference to the requested element.
		 * @exception std::runtime_error - if given "index" is greater or equal to container size, or less than 0.
		 */
		[[nodiscard]] auto operator[](const int index) const -> const T&;

		/**
		 * @brief Returns a reference to the element at specified location "index".
		 * @param index - position of the element to return
		 * @return Reference to the requested element.
		 * @exception std::runtime_error - if given "index" is greater or equal to container size, or less than 0.
		 */
		auto operator[](const int index) -> T&;

	private:

		/**
		 * @brief Internal buffer for data.
		 */
		std::unique_ptr<T[]> m_data;

		/**
		 * @brief Accessible size of internal buffer.
		 */
		int m_size;

		/**
		 * @brief Allocated size of internal buffer.
		 */
		int m_capacity;

		/**
		 * @brief Offset to first element in internal buffer.
		 */
		int m_offset;


		/**
		 * @brief Constructs the container with given "size" and, and given "capacity" if "forceCapacity" is true.
		 * @param size - the size of the container
		 * @param forceCapacity - force container capacity to have value "capacity"
		 * @param capacity - forced container capacity, has effect only if "forceCapacity" is true
		 * @exception std::runtime_error - if allocation of internal buffer fails
		 * or any exception is thrown by the constructor of T; if thrown, this constructor has no effect.
		 */
		[[nodiscard]] JippVector(const int size, bool forceCapacity, const int capacity = 0);

		/**
		 * @brief Constructs the container with the contents of the "range", and given "capacity" if "forceCapacity" is true.
		 * @param range - a container compatible range, that is, an input_range whose elements are convertible to T
		 * @param forceCapacity - force container capacity to have value "capacity"
		 * @param capacity - forced container capacity, has effect only if "forceCapacity" is true
		 * @exception std::runtime_error - if allocation of internal buffer fails
		 * or any exception is thrown by the constructor of T; if thrown, this constructor has no effect.
		 */
		[[nodiscard]] JippVector(std::ranges::input_range auto&& range, bool forceCapacity, const int capacity);


		/**
		 * @brief Calculates new capacity for container with reserve for further insertion;
		 * if "force" is true, returned capacity is the same as "requestedCapacity".
		 * @param requestedCapacity - minimal capacity required
		 * @return New capacity for container.
		*/
		[[nodiscard]] auto getNewCapacity(const int requestedCapacity) const noexcept -> int;

		/**
		 * @brief Calculates offset to first element in internal buffer. Should be called immediately agter changing "m_capacity".
		 * @return Offset to first element.
		 */
		[[nodiscard]] auto getOffset(void) const noexcept -> int;

		/**
		 * @brief Allocates internal buffer for data.
		 * @exception std::runtime_error - if allocation of internal buffer fails
		 * or any exception is thrown by the constructor of T; if thrown, this method has no effect.
		 */
		auto allocate(void) -> void;

		/**
		 * @brief Copies "range" to "destination".
		 * @param range - range to be copied to "destination"
		 * @param destination - destination to which "range" has to be copied
		 * @exception std::runtime_error - if any exception is thrown by the constructor of T;
		 * if thrown, this constructor has no effect.
		 */
		auto copy(std::ranges::input_range auto&& range, T* const destination) -> void;

		/**
		 * @brief Change the capacity of the container to a value that's equal to "newCapacity".
		 * @param newCapacity - new capacity of the container, in number of elements
		 * @exception std::runtime_error - if "newCapacity" is less than size,
		 * or allocation of internal buffer fails or any exception is thrown by the constructor of T.
		 */
		auto reserveInternal(const int newCapacity) -> void;


		/**
		 * @brief Minimal container size.
		 */
		inline static int MINIMAL_SIZE = 0;

		/**
		 * @brief Minimal container capacity, unless JippVector(int) or shrinkToFit() was called.
		 */
		inline static int MINIMAL_CAPACITY = 4;

		/**
		 * @brief Growth/shrink factor for automatic reallocation.
		 */
		inline static int REALLOCATION_FACTOR = 2;


		/**
		 * @brief Deleted copy assignment operator.
		 */
		void operator=(const JippVector&) = delete;


		/**
		 * @brief Friend function for accessing internal buffer, used only for testing.
		 */
		friend auto _internal::_extract(const JippVector<T>&) noexcept -> T*;
	};

#pragma region Implementation

#pragma region Public

#pragma region Constructors

	template <concepts::Storable T>
	[[nodiscard]] JippVector<T>::JippVector(void)
		: JippVector(MINIMAL_SIZE, false) { }

	template<concepts::Storable T>
	[[nodiscard]] JippVector<T>::JippVector(const int capacity)
		: JippVector(MINIMAL_SIZE, true, capacity) { }

	template <concepts::Storable T>
	[[nodiscard]] JippVector<T>::JippVector(const std::initializer_list<T> data)
		: JippVector(data | std::views::all) { }

	template<concepts::Storable T>
	[[nodiscard]] JippVector<T>::JippVector(const JippVector& other)
		: JippVector(other | std::views::all, true, other.capacity()) { }

	template <concepts::Storable T>
	[[nodiscard]] JippVector<T>::JippVector(std::ranges::input_range auto&& range)
		: JippVector(range, false, 0) { }

#pragma endregion Constructors

#pragma region Methods

	template <concepts::Storable T>
	[[nodiscard]] auto JippVector<T>::capacity(void) const noexcept -> int
	{
		return m_capacity;
	}

	template <concepts::Storable T>
	[[nodiscard]] auto JippVector<T>::size(void) const noexcept -> int
	{
		return m_size;
	}

	template<concepts::Storable T>
	auto JippVector<T>::isEmpty(void) const noexcept -> bool
	{
		return m_size == 0;
	}

	template <concepts::Storable T>
	[[nodiscard]] auto JippVector<T>::begin(void) const noexcept -> const T*
	{
		return m_data.get() + m_offset;
	}

	template <concepts::Storable T>
	[[nodiscard]] auto JippVector<T>::begin(void) noexcept -> T*
	{
		return const_cast<T*>(const_cast<const JippVector<T>*>(this)->begin());
	}

	template <concepts::Storable T>
	[[nodiscard]] auto JippVector<T>::end(void) const noexcept -> const T*
	{
		return begin() + m_size;
	}

	template <concepts::Storable T>
	[[nodiscard]] auto JippVector<T>::end(void) noexcept -> T*
	{
		return const_cast<T*>(const_cast<const JippVector<T>*>(this)->end());
	}

	template <concepts::Storable T>
	auto JippVector<T>::fill(const T& value) -> void
	{
		fill(0, size(), value);
	}

	template <concepts::Storable T>
	auto JippVector<T>::fill(const int start, const int count, const T& value) -> void
	{
		_internal::_throwOnFailedPredicate(start >= 0, "Start cannot be negative");
		_internal::_throwOnFailedPredicate(start < m_size, "Start cannot be greater or equal to container size");
		_internal::_throwOnFailedPredicate(count >= 0, "Count cannot be negative");
		_internal::_throwOnFailedPredicate(start + count <= m_size, "Start and count sum cannot exceed container size");
		begin_save_except
			std::fill(begin() + start, begin() + start + count, value);
		end_save_except
	}

	template<concepts::Storable T>
	auto JippVector<T>::reserve(const int newCapacity) -> void
	{
		_internal::_throwOnFailedPredicate(newCapacity >= m_size, "New capacity cannot be less than size");
		if (newCapacity <= m_capacity) [[unlikely]]
			return;
		reserveInternal(newCapacity);
	}

	template<concepts::Storable T>
	auto JippVector<T>::pushBack(const T& value) -> void
	{
		if (m_offset + m_size == m_capacity)
			reserve(m_capacity * REALLOCATION_FACTOR);
		*end() = value;
		m_size++;
	}

	template<concepts::Storable T>
	auto JippVector<T>::pushFront(const T& value) -> void
	{
		if (m_offset == 0)
			reserve(m_capacity * REALLOCATION_FACTOR);
		*(begin() - 1) = value;
		m_size++;
		m_offset--;
	}

	template<concepts::Storable T>
	auto JippVector<T>::insert(const int index, const T& value) -> void
	{
		_internal::_throwOnFailedPredicate(index >= 0, "Index cannot be negative");
		_internal::_throwOnFailedPredicate(index <= m_size, "Index cannot be greater than container size");
		if (index == 0)
		{
			pushFront(value);
			return;
		}
		if (index == m_size)
		{
			pushBack(value);
			return;
		}
		if (m_size == m_capacity)
			reserve(m_capacity * 2);
		if (m_offset == 0)
			copy(std::span{ begin() + index, end() }, begin() + index + 1);
		else
		{
			copy(std::span{ begin(), begin() + index }, begin() - 1);
			m_offset--;
		}
		m_size++;
		operator[](index) = value;
	}

	template <concepts::Storable T>
	auto JippVector<T>::erase(const int start, const int count) -> void
	{
		_internal::_throwOnFailedPredicate(start >= 0, "Start cannot be negative");
		_internal::_throwOnFailedPredicate(start < m_size, "Start cannot be greater or equal to container size");
		_internal::_throwOnFailedPredicate(count > 0, "Count cannot be negative or equal to zero");
		_internal::_throwOnFailedPredicate(start + count <= m_size, "Start and count sum cannot exceed container size");
		if (start < m_size - start - count)
		{
			copy(std::span{ begin(), begin() + start }, begin() + count);
			m_offset += count;
		}
		else
			copy(std::span{ begin() + start + count, end() }, begin() + start);
		m_size -= count;
		if (m_size * 4 < m_capacity)
			reserveInternal(m_capacity / 2);
	}

	template <concepts::Storable T>
	auto JippVector<T>::erase(const int index) -> void
	{
		erase(index, 1);
	}

	template <concepts::Storable T>
	auto JippVector<T>::shrinkToFit(void) -> void
	{
		reserveInternal(m_size == 0 ? 1 : m_size);
	}

	template<concepts::Storable T>
	auto JippVector<T>::clear(void) noexcept -> void
	{
		m_size = 0;
		m_offset = getOffset();
	}

#pragma endregion Methods

#pragma region Operators

	template <concepts::Storable T>
	[[nodiscard]] auto JippVector<T>::operator[](const int index) const -> const T&
	{
		_internal::_throwOnFailedPredicate(index >= 0, "Index cannot be negative");
		_internal::_throwOnFailedPredicate(index < m_size, "Index cannot be greater or equal to container size");
		return *(begin() + index);
	}

	template<concepts::Storable T>
	auto JippVector<T>::operator[](const int index) -> T&
	{
		return const_cast<T&>(const_cast<const JippVector<T>*>(this)->operator[](index));
	}

#pragma endregion Operators

#pragma endregion Public

#pragma region Private

#pragma region Constructors

	template <concepts::Storable T>
	[[nodiscard]] JippVector<T>::JippVector(const int size, const bool forceCapacity, const int capacity)
		:m_size{ size }, m_capacity{ getNewCapacity(m_size) }, m_offset{ getOffset() }
	{
		if (forceCapacity) [[unlikely]]
		{
			_internal::_throwOnFailedPredicate(capacity > 0, "Capacity cannot be negative or zero");
			m_capacity = capacity;
		}
		allocate();
	}

	template <concepts::Storable T>
	[[nodiscard]] JippVector<T>::JippVector(std::ranges::input_range auto&& range, const bool forceCapacity, const int capacity)
		: JippVector(static_cast<int>(range.size()), forceCapacity, capacity)
	{
		copy(range, begin());
	}

#pragma endregion Constructors

#pragma region Methods

	template <concepts::Storable T>
	[[nodiscard]] auto JippVector<T>::getNewCapacity(const int requestedCapacity) const noexcept -> int
	{
		int newCapacity = MINIMAL_CAPACITY;
		while (newCapacity <= requestedCapacity)
			newCapacity *= 2;
		return newCapacity;
	}

	template <concepts::Storable T>
	[[nodiscard]] auto JippVector<T>::getOffset(void) const noexcept -> int
	{
		return (m_capacity - m_size) / 2;
	}

	template<concepts::Storable T>
	auto JippVector<T>::allocate(void) -> void
	{
		begin_save_except
			m_data = std::make_unique_for_overwrite<T[]>(static_cast<std::size_t>(m_capacity));
		end_save_except
	}

	template<concepts::Storable T>
	auto JippVector<T>::copy(std::ranges::input_range auto&& range, T* const destination) -> void
	{
		begin_save_except
			std::ranges::copy(range, destination);
		end_save_except
	}

	template<concepts::Storable T>
	auto JippVector<T>::reserveInternal(const int newCapacity) -> void
	{
		_internal::_throwOnFailedPredicate(newCapacity >= m_size, "New capacity cannot be less than size");
		auto deleter = m_data.get_deleter();
		T* const oldData = m_data.release();
		const std::span data = std::span{ oldData + m_offset, oldData + m_offset + m_size };
		m_capacity = newCapacity;
		m_offset = getOffset();
		allocate();
		copy(data, begin());
		deleter(oldData);
	}

#pragma endregion Methods

#pragma endregion Private

#pragma region Internal

	namespace _internal
	{
		template <concepts::Storable T>
		[[nodiscard]] auto _extract(const JippVector<T>& vector) noexcept -> T*
		{
			return vector.m_data.get();
		}

		auto _safeExcept(const concepts::Invocable auto& functor) -> decltype(std::invoke(functor))
		{
			try
			{
				return std::invoke(functor);
			}
			catch (const std::exception& ex)
			{
				std::stringstream stream;
				stream << "Exception " << typeid(ex).name() << " thrown: " << ex.what() << "\nStacktrace:\n" << std::stacktrace::current() << "\n";
				std::throw_with_nested(std::runtime_error(stream.str()));
			}
		}
	}

#pragma endregion Internal

#pragma endregion Implementation

}
