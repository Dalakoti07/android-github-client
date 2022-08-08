package com.dalakoti07.android.core.data.state

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object ParsingError : Failure()
    object UnauthorizedError : Failure()
    object UnknownError: Failure()

    abstract class FeatureFailure : Failure()
}