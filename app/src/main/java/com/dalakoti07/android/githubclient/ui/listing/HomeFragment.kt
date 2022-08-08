package com.dalakoti07.android.githubclient.ui.listing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.dalakoti07.android.githubclient.databinding.HomeFragmentBinding
import com.dalakoti07.android.githubclient.ui.MainViewModel
import com.dalakoti07.android.githubclient.ui.adapters.PullRequestsAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val TAG = "HomeFragment"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var pullRequestsAdapter: PullRequestsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setObservers() {
        // show error events
        viewModel.events.onEach {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }.launchIn(
            lifecycleScope
        )

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isInvisible = !loading
        }

        viewModel.allPrs.observe(viewLifecycleOwner) { prs ->
            pullRequestsAdapter.submitList(prs)
        }
    }

    private fun setViews() {
        pullRequestsAdapter = PullRequestsAdapter { item ->
            // launch chrome
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(item.url)
                )
            )
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}