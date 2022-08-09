package com.dalakoti07.android.githubclient.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.os.Looper
import com.dalakoti07.android.core.repository.FakeGithubService
import com.dalakoti07.android.core.usecases.FetchPullRequestsUseCase
import com.dalakoti07.android.core.utils.MainCoroutineRule
import com.dalakoti07.android.githubclient.data.GithubRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.*

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockkStatic(Looper::class)

        val looper = mockk<Looper> {
            every { thread } returns Thread.currentThread()
        }

        every { Looper.getMainLooper() } returns looper

        // inject dependency manually
        viewModel = MainViewModel(
            fetchPullRequestsUseCase = FetchPullRequestsUseCase(
                repository = GithubRepositoryImpl(
                    githubService = FakeGithubService()
                )
            )
        )
    }

    @Test
    fun `fetch pull requests successfully`() = runTest {
        Assert.assertEquals(
            false,
            viewModel.isLoading.value
        )
        // assert size is 30
        Assert.assertEquals(
            30,
            viewModel.allPrs.value!!.size,
        )
        val prsList = viewModel.allPrs.value!!

        // assert values

        Assert.assertEquals(
            "Java 17",
            prsList[0].title
        )
        Assert.assertEquals(
            "JakeWharton",
            prsList[0].user.name
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

}