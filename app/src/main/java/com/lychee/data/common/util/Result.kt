package com.lychee.data.common.util

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Success<*> -> "Success"
            is Error -> "Error"
            Loading -> "Loading"
        }
    }
}