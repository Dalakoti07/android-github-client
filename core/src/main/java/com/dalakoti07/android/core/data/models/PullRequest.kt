package com.dalakoti07.android.core.data.models

import com.google.gson.annotations.SerializedName

data class PullRequest(
    val url: String,
    val id: Long,
    val title: String,
    val user: User,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("closed_at")
    val closedAt: String,
)
