package com.dalakoti07.android.core.network

import com.dalakoti07.android.core.data.models.PullRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("square/retrofit/pulls")
    suspend fun getSupportTicketCategories(
        @Query("state") state: String,
        @Query("per_page") itemPerPage: Int,
    ): Response<List<PullRequest>>

}
