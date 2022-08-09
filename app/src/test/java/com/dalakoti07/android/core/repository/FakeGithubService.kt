package com.dalakoti07.android.core.repository

import com.dalakoti07.android.core.data.models.PullRequest
import com.dalakoti07.android.core.network.GithubService
import com.dalakoti07.android.core.utils.BaseFakeService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.lang.reflect.Type

fun <T> getList(jsonArray: String?, clazz: Class<T>?): List<T>? {
    val typeOfT: Type = TypeToken.getParameterized(MutableList::class.java, clazz).type
    return Gson().fromJson(jsonArray, typeOfT)
}

@Suppress("JoinDeclarationAndAssignment")
class FakeGithubService: BaseFakeService(),GithubService  {

    private val pullRequestsString = readJson("assets/success_response.json")

    private var prs: List<PullRequest>

    init {
        prs = getList(
            pullRequestsString,
            PullRequest::class.java
        )?: emptyList()
    }

    override suspend fun getSupportTicketCategories(
        state: String,
        itemPerPage: Int,
        page: Int
    ): Response<List<PullRequest>> {
        return Response.success(
            prs
        )
    }
}