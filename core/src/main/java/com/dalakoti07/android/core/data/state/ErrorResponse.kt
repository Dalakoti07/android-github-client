package com.dalakoti07.android.core.data.state

data class ErrorResponse(
    var message: String?,
    var exception: Exception? = null
)
