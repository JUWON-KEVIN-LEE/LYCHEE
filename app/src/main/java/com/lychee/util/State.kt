package com.lychee.util

sealed class State<out T> {
    class Init : State<Nothing>()
    class Loading : State<Nothing>()
    class Success<out T>(data : T) : State<T>()
    class Error(throwable: Throwable) : State<Nothing>()
}