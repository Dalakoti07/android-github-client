package com.dalakoti07.android.githubclient.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dalakoti07.android.githubclient.databinding.ActivityMainBinding
import com.dalakoti07.android.githubclient.ui.adapters.PullRequestsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var pullRequestsAdapter: PullRequestsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()

        setObservers()
    }

    private fun setObservers() {
        // show error events
        viewModel.events.onEach {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.isLoading.observe(this){ loading->
            binding.progressBar.isInvisible = !loading
        }

        viewModel.allPrs.observe(this) { prs->
            pullRequestsAdapter.submitList(prs)
        }
    }

    private fun setViews() {
        pullRequestsAdapter = PullRequestsAdapter { item->
            // launch chrome
        }

        binding.rvPrs.adapter = pullRequestsAdapter

        binding.rvPrs.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.rvPrs.canScrollVertically(1)) {
                    viewModel.loadNextPage()
                }
            }
        })
    }

}
