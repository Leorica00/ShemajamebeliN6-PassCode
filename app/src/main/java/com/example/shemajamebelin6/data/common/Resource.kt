package com.example.shemajamebelin6.data.common

sealed class Resource<T>(
    val isLoading: Boolean = false,
    val successData: T? = null,
    val errorMessage: String = ""
) {
    data class Loading<T>(val loading: Boolean) : Resource<T>(isLoading = loading)
    data class Success<T>(val response: T) :
        Resource<T>(successData = response)
    data class Error<T>(val message: String) : Resource<T>(errorMessage = message)
}