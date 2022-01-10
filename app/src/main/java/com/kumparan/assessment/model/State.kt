package com.kumparan.assessment.model

import com.kumparan.assessment.data.repository.Resource

/**
 * state management untuk data & ui
 */
sealed class State<T> {
    class Loading<T> : State<T>()

    data class Success<T>(val data: T) : State<T>()

    data class Error<T>(val message: String) : State<T>()

    fun isLoading(): Boolean = this is Loading

    fun isSuccessful(): Boolean = this is Success

    fun isFailed(): Boolean = this is Error

    companion object {

        /**
         * Return [State.Loading]
         */
        fun <T> loading() = Loading<T>()

        /**
         * Return [State.Success]
         * @param data Data yang di emit
         */
        fun <T> success(data: T) =
            Success(data)

        /**
         * Return [State.Error]
         * @param message pesan error
         */
        fun <T> error(message: String) =
            Error<T>(message)

        /**
         * Return [State] dari [Resource]
         */
        fun <T> fromResource(resource: Resource<T>): State<T> = when (resource) {
            is Resource.Success -> success(resource.data)
            is Resource.Failed -> error(resource.message)
        }
    }
}