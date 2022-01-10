package com.kumparan.assessment.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kumparan.assessment.data.local.KumparanDatabase
import com.kumparan.assessment.model.Photo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotosDaoTest {

    private lateinit var mDatabase: KumparanDatabase

    @Before
    fun init() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            KumparanDatabase::class.java
        ).build()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insert_and_select_photos() = runBlocking {
        val photos = listOf(
            Photo(1, title = "photo 1"),
            Photo(2, title = "photo 2")
        )

        mDatabase.getPhotosDao().addPhotos(photos)

        val dbPhotos = mDatabase.getPhotosDao().getAllPhotos().first()

        assertThat(dbPhotos, equalTo(photos))
    }

    @Test
    @Throws(InterruptedException::class)
    fun select_photo_by_id() = runBlocking {
        val photos = listOf(
            Photo(1, title = "photo 1"),
            Photo(2, title = "photo 2")
        )

        mDatabase.getPhotosDao().addPhotos(photos)

        var dbPhoto = mDatabase.getPhotosDao().getPhotoById(1).first()
        assertThat(dbPhoto, equalTo(photos[0]))

        dbPhoto = mDatabase.getPhotosDao().getPhotoById(2).first()
        assertThat(dbPhoto, equalTo(photos[1]))
    }

    @After
    fun cleanup() {
        mDatabase.close()
    }
}