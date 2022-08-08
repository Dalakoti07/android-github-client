package com.dalakoti07.android.githubclient.di

import com.dalakoti07.android.core.repository.GithubRepository
import com.dalakoti07.android.githubclient.data.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGithubRepository(
        profileRepositoryImpl: GithubRepositoryImpl
    ): GithubRepository

}