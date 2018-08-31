package com.lychee.data.common

sealed class Resource<out T> {
    class Loading : Resource<Nothing>()
    class Success<out T>(val data: T) : Resource<T>()
    class Error(val error: String) : Resource<Nothing>()
}