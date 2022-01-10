package com.kumparan.assessment.di.module

import com.kumparan.assessment.data.repository.DefaultAlbumRespository
import com.kumparan.assessment.data.repository.AlbumRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class AlbumRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindAlbumRepository(repository: DefaultAlbumRespository): AlbumRepository
}