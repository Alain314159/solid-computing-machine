package com.cerdita.app.util

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
    
    inline fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }
    
    inline fun onFailure(action: (Throwable) -> Unit): Result<T> {
        if (this is Failure) action(exception)
        return this
    }
}
