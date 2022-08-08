package com.dalakoti07.android.core.usecases

import com.dalakoti07.android.core.data.models.PullRequest
import com.dalakoti07.android.core.data.state.ErrorResponse
import com.dalakoti07.android.core.data.state.Failure
import com.dalakoti07.android.core.data.state.Resource
import com.dalakoti07.android.core.repository.GithubRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import javax.inject.Inject

class FetchPullRequestsUseCase @Inject constructor(
    private val repository: GithubRepository,
) {

    operator fun invoke(page: Int): Flow<Resource<List<PullRequest>>> = flow {
        try {
            emit(Resource.Loading<List<PullRequest>>())
            val response = repository.fetchPullRequests(page)
            if (response.isSuccessful) {
                val responseList = response.body()!!
                val finalList = responseList.map {

                }
                emit(
                    Resource.Success<List<PullRequest>>(response.body()!!)
                )
            } else {
                val json = response.errorBody()?.string()
                val error = Gson().fromJson(json, ErrorResponse::class.java)
                if (response.code() == 401) {
                    emit(
                        Resource.Error<List<PullRequest>>(
                            Pair(
                                Failure.UnauthorizedError, error
                            )
                        )
                    )
                } else {
                    emit(
                        Resource.Error<List<PullRequest>>(
                            Pair(
                                Failure.ServerError,
                                error
                            )
                        )
                    )
                }
            }
        } catch (exception: Exception) {
            if (exception is JSONException) {
                emit(
                    Resource.Error<List<PullRequest>>(
                        Pair(
                            Failure.ParsingError,
                            ErrorResponse(exception.message, exception)
                        )
                    )
                )
            } else {
                emit(
                    Resource.Error<List<PullRequest>>(
                        Pair(
                            Failure.NetworkConnection,
                            ErrorResponse(exception.message, exception)
                        )
                    )
                )
            }
        }
    }

}