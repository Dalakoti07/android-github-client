package com.dalakoti07.android.core.data.models

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("html_url")
    val url: String,
    val id: Long,
    val title: String,
    val user: User,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("closed_at")
    var closedAt: String,
){

    class DiffUtilCallBack : DiffUtil.ItemCallback<PullRequest>() {
        override fun areItemsTheSame(
            oldItem: PullRequest,
            newItem: PullRequest
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PullRequest,
            newItem: PullRequest
        ): Boolean = oldItem == newItem
    }
}
