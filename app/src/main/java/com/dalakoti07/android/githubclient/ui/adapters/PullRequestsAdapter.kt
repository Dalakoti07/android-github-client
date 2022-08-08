package com.dalakoti07.android.githubclient.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.dalakoti07.android.core.data.models.PullRequest
import com.dalakoti07.android.githubclient.databinding.ItemPullRequestBinding

class PullRequestsAdapter constructor(private val onClicked: (PullRequest) -> Unit) :
    ListAdapter<PullRequest, PullRequestViewHolder>(PullRequest.DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        return PullRequestViewHolder(
            ItemPullRequestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.setItem(pr = currentList[position], onClicked = onClicked)
    }

}