package com.dalakoti07.android.githubclient.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.dalakoti07.android.core.data.models.PullRequest
import com.dalakoti07.android.githubclient.databinding.ItemPullRequestBinding
import com.dalakoti07.android.githubclient.ui.loadImageByUrl

class PullRequestViewHolder(
    private val binding: ItemPullRequestBinding
): RecyclerView.ViewHolder(binding.root) {

    fun setItem(pr: PullRequest, onClicked: (PullRequest) -> Unit){
        binding.tvClosedOn.text = pr.closedAt
        binding.tvCreatedAt.text = pr.createdAt
        binding.tvCreatedBy.text = pr.user.name

        binding.tvTitle.text = pr.title

        binding.ivAvatar.loadImageByUrl(pr.user.avatar)

        binding.root.setOnClickListener {
            onClicked.invoke(pr)
        }
    }
}