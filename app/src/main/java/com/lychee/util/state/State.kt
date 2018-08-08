package com.lychee.util.state

sealed class Resource<out T> {
    class Loading : Resource<Nothing>()
    class Success<out T>(data: T) : Resource<T>()
    class Error(message: String) : Resource<Nothing>()
}