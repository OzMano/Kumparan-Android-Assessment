package com.kumparan.assessment.di.module

import android.app.Application
import com.kumparan.assessment.data.local.KumparanDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class KumparanDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = KumparanDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providePostsDao(database: KumparanDatabase) = database.getPostsDao()

    @Singleton
    @Provides
    fun providePhotosDao(database: KumparanDatabase) = database.getPhotosDao()

    @Singleton
    @Provides
    fun provideAlbumsDao(database: KumparanDatabase) = database.getAlbumsDao()

    @Singleton
    @Provides
    fun provideCommentsDao(database: KumparanDatabase) = database.getCommentsDao()
}