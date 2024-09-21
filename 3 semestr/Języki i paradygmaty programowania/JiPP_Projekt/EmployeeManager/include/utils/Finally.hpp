#pragma once

#include "utils/Concepts.hpp"

/**
 * @brief Creates action to be executed at end of code block. Should be assigned to temporary variable.
 * Any exception thrown in "functor" is ommited.
 * @param functor - action to be executed at the end of code block
 */
#define finally(functor) (utils::finally::_internal::FinalAction([&]() noexcept { try functor catch (...) {} }))

/**
 * @brief Namespace containing common utils.
 */
namespace utils
{
	/**
	 * @brief Namespace containing finally helpers.
	 */
	namespace finally
	{
		/**
		 * @brief Internal namespace, NOT FOR EXTERNAL USE!
		 */
		namespace _internal
		{
			/**
			 * @brief Final action executor.
			 * @tparam T - type of action to execute; must fulfill NoexceptInvocable constraint
			 */
			template <concepts::NoexceptInvocable T>
			class FinalAction final
			{
			public:

				/**
				 * @brief Constructs final action executor.
				 * @param functor - action to be executed at the end of code block
				 */
				[[nodiscard]] explicit FinalAction(const T& functor) noexcept;

				/**
				 * @brief Destructs the executor. Execution of final action is done before destruction.
				 */
				~FinalAction(void) noexcept;


				/**
				 * @brief Disables execution of final action.
				 */
				auto disable(void) noexcept -> void;

			private:

				/**
				 * @brief Internal holder for functor to execute.
				 */
				T m_functor;

				/**
				 * @brief Describes if "m_functor" should be executed in destructor.
				 */
				bool enabled = true;


				/**
				 * @brief Deleted default constructor.
				 */
				FinalAction(void) = delete;

				/**
				 * @brief Deleted copy constructor.
				 */
				FinalAction(const FinalAction&) = delete;

				/**
				 * @brief Deleted copy assignment operator.
				 */
				void operator=(const FinalAction&) = delete;
			};

#pragma region Implementation

#pragma region Internal

			template <concepts::NoexceptInvocable T>
			[[nodiscard]] FinalAction<T>::FinalAction(const T& functor) noexcept
				: m_functor{ functor } { }

			template <concepts::NoexceptInvocable T>
			FinalAction<T>::~FinalAction(void) noexcept
			{
				if (enabled)
					std::invoke(m_functor);
			}

			template <concepts::NoexceptInvocable T>
			auto FinalAction<T>::disable(void) noexcept -> void
			{
				enabled = false;
			}

#pragma endregion

#pragma endregion Implementation

		}
	}
}
