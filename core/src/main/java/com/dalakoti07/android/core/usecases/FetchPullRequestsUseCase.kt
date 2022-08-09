package com.dalakoti07.android.core.usecases

import com.dalakoti07.android.core.data.models.PullRequest
import com.dalakoti07.android.core.data.state.ErrorResponse
import com.dalakoti07.android.core.data.state.Failure
import com.dalakoti07.android.core.data.state.Resource
import com.dalakoti07.android.core.utils.DateFormatter
import com.dalakoti07.android.core.repository.GithubRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.json.JSONException
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class FetchPullRequestsUseCase @Inject constructor(
    private val repository: GithubRepository,
) {

    private val dateFormatter = DateFormatter()

    operator fun invoke(page: Int): Flow<Resource<List<PullRequest>>> = channelFlow{
        try {
            send(Resource.Loading<List<PullRequest>>())
            val response = repository.fetchPullRequests(page)
            if (response.isSuccessful) {
                val responseList = response.body()!!

                // do mapping for some thing computational in IO thread
                withContext(Dispatchers.IO){
                    responseList.map {
                        it.closedAt = dateFormatter.formatDate(it.closedAt)
                        it.createdAt = dateFormatter.formatDate(it.createdAt)
                    }
                }
                send(
                    Resource.Success<List<PullRequest>>(responseList)
                )
            } else {
                val json = response.errorBody()?.string()
                val error = Gson().fromJson(json, ErrorResponse::class.java)
                if (response.code() == 401) {
                    send(
                        Resource.Error<List<PullRequest>>(
                            Pair(
                                Failure.UnauthorizedError, error
                            )
                        )
                    )
                } else {
                    send(
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
                send(
                    Resource.Error<List<PullRequest>>(
                        Pair(
                            Failure.ParsingError,
                            ErrorResponse(exception.message, exception)
                        )
                    )
                )
            } else {
                send(
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