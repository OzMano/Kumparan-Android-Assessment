package com.kumparan.assessment.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kumparan.assessment.data.local.KumparanDatabase
import com.kumparan.assessment.model.Album
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumsDaoTest {

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
    fun insert_and_select_albums() = runBlocking {
        val albums = listOf(
            Album(1, title = "album 1"),
            Album(2, title = "album 2")
        )

        mDatabase.getAlbumsDao().addAlbums(albums)

        val dbAlbums = mDatabase.getAlbumsDao().getAllAlbums().first()

        assertThat(dbAlbums, equalTo(albums))
    }

    @Test
    @Throws(InterruptedException::class)
    fun select_album_by_id() = runBlocking {
        val albums = listOf(
            Album(1, title = "album 1"),
            Album(2, title = "album 2")
        )

        mDatabase.getAlbumsDao().addAlbums(albums)

        var dbAlbum = mDatabase.getAlbumsDao().getAlbumById(1).first()
        assertThat(dbAlbum, equalTo(albums[0]))

        dbAlbum = mDatabase.getAlbumsDao().getAlbumById(2).first()
        assertThat(dbAlbum, equalTo(albums[1]))
    }

    @After
    fun cleanup() {
        mDatabase.close()
    }
}