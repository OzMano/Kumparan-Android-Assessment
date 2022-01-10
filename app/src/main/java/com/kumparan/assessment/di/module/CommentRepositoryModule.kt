package com.kumparan.assessment.di.module

import com.kumparan.assessment.data.repository.DefaultCommentRespository
import com.kumparan.assessment.data.repository.CommentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class CommentRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindCommentRepository(repository: DefaultCommentRespository): CommentRepository
}