package com.kumparan.assessment.di.module

import com.kumparan.assessment.data.repository.DefaultPhotoRespository
import com.kumparan.assessment.data.repository.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class PhotoRepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindPhotoRepository(repository: DefaultPhotoRespository): PhotoRepository
}