package com.kumparan.assessment.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kumparan.assessment.data.local.KumparanDatabase
import com.kumparan.assessment.model.Comment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommentsDaoTest {

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
    fun insert_and_select_comments() = runBlocking {
        val comments = listOf(
            Comment(1, name = "comment 1"),
            Comment(2, name = "comment 2")
        )

        mDatabase.getCommentsDao().addComments(comments)

        val dbComments = mDatabase.getCommentsDao().getAllComments().first()

        assertThat(dbComments, equalTo(comments))
    }

    @Test
    @Throws(InterruptedException::class)
    fun select_comment_by_id() = runBlocking {
        val comments = listOf(
            Comment(1, name = "comment 1"),
            Comment(2, name = "comment 2")
        )

        mDatabase.getCommentsDao().addComments(comments)

        var dbComment = mDatabase.getCommentsDao().getCommentById(1).first()
        assertThat(dbComment, equalTo(comments[0]))

        dbComment = mDatabase.getCommentsDao().getCommentById(2).first()
        assertThat(dbComment, equalTo(comments[1]))
    }

    @After
    fun cleanup() {
        mDatabase.close()
    }
}