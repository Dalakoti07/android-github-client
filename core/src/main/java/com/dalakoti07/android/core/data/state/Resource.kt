package com.dalakoti07.android.core.data.state

sealed class Resource<T>(val data: T? = null, val error: Pair<Failure, ErrorResponse>? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(error: Pair<Failure, ErrorResponse>?, data: T? = null) : Resource<T>(data, error)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
