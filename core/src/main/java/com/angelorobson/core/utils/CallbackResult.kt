package com.angelorobson.core.utils


sealed class CallbackResult<T> {
    data class Success<T>(val data: T?) : CallbackResult<T>()
    data class Error<T>(val message: String?) : CallbackResult<T>()
    data class Loading<T>(val nothing: Nothing? = null) : CallbackResult<T>()
}