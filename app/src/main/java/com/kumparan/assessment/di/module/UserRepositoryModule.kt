package com.kumparan.assessment.di.module

import com.kumparan.assessment.data.repository.DefaultUserRespository
import com.kumparan.assessment.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class UserRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindUserRepository(repository: DefaultUserRespository): UserRepository
}