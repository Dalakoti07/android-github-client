package com.dalakoti07.android.githubclient.data

import com.dalakoti07.android.core.data.models.PullRequest
import com.dalakoti07.android.core.network.GithubService
import com.dalakoti07.android.core.repository.GithubRepository
import retrofit2.Response
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubService: GithubService
): GithubRepository {

    override suspend fun fetchPullRequests(): Response<List<PullRequest>> {
        return githubService.getSupportTicketCategories(
            state = "closed",
            itemPerPage = 10,
        )
    }

}