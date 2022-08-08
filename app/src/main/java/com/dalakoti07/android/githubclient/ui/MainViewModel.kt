package com.dalakoti07.android.githubclient.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dalakoti07.android.core.data.models.PullRequest
import com.dalakoti07.android.core.data.state.Resource
import com.dalakoti07.android.core.usecases.FetchPullRequestsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchPullRequestsUseCase: FetchPullRequestsUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _allPrs = MutableLiveData<List<PullRequest>>()
    val allPrs: LiveData<List<PullRequest>>
        get() = _allPrs

    private val _events = Channel<String>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        getAllPullRequests()
    }

    private fun getAllPullRequests() {
        fetchPullRequestsUseCase()
            .onEach { response ->
                when (response) {
                    is Resource.Success<List<PullRequest>> -> {
                        _isLoading.value = false
                        response.data?.let {
                            _allPrs.value = it
                        }
                    }
                    is Resource.Error<List<PullRequest>> -> {
                        _events.send(response.error?.second?.message ?: "Unknown error")
                        _isLoading.value = false
                    }
                    is Resource.Loading<List<PullRequest>> -> {
                        _isLoading.value = true
                    }
                }
            }
            .launchIn(
                viewModelScope
            )
    }


}