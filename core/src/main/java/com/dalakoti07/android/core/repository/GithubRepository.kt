package com.dalakoti07.android.core.repository

import com.dalakoti07.android.core.data.models.PullRequest
import retrofit2.Response

interface GithubRepository {

    suspend fun fetchPullRequests(page: Int): Response<List<PullRequest>>

}