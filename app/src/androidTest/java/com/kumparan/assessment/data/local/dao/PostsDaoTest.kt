package com.kumparan.assessment.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kumparan.assessment.data.local.KumparanDatabase
import com.kumparan.assessment.model.Post
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostsDaoTest {

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
    fun insert_and_select_posts() = runBlocking {
        val posts = listOf(
            Post(1, title = "post 1"),
            Post(2, title = "post 2")
        )

        mDatabase.getPostsDao().addPosts(posts)

        val dbPosts = mDatabase.getPostsDao().getAllPosts().first()

        assertThat(dbPosts, equalTo(posts))
    }

    @Test
    @Throws(InterruptedException::class)
    fun select_post_by_id() = runBlocking {
        val posts = listOf(
            Post(1, title = "post 1"),
            Post(2, title = "post 2")
        )

        mDatabase.getPostsDao().addPosts(posts)

        var dbPost = mDatabase.getPostsDao().getPostById(1).first()
        assertThat(dbPost, equalTo(posts[0]))

        dbPost = mDatabase.getPostsDao().getPostById(2).first()
        assertThat(dbPost, equalTo(posts[1]))
    }

    @After
    fun cleanup() {
        mDatabase.close()
    }
}