package com.kumparan.assessment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kumparan.assessment.model.Comment
import kotlinx.coroutines.flow.Flow

/**
 * dao untuk [com.kumparan.assessment.data.local.KumparanDatabase]
 */
@Dao
interface CommentsDao {

    /**
     * menambahkan [comments] ke table [Comment.TABLE_NAME]
     * nilai yang sama pada table akan otomatis di replace
     * @param comments typicode comments
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComments(comments: List<Comment>)

    /**
     * menghapus semua comment dari table [Comment.TABLE_NAME]
     */
    @Query("DELETE FROM ${Comment.TABLE_NAME}")
    suspend fun deleteAllComments()

    /**
     * mengambil comment dari table [Comment.TABLE_NAME] jika id nya sama dengan [commentId]
     * @param commentId comment id dari [Comment]
     */
    @Query("SELECT * FROM ${Comment.TABLE_NAME} WHERE ID = :commentId")
    fun getCommentById(commentId: Int): Flow<Comment>

    /**
     * mengambil list comment dari table [Comment.TABLE_NAME] jika post id nya sama dengan [commentId]
     * @param postId post id
     */
    @Query("SELECT * FROM ${Comment.TABLE_NAME} WHERE postId = :postId")
    fun getCommentByPostId(postId: Int): Flow<List<Comment>>

    /**
     * mengambil semua comment dari table [Comment.TABLE_NAME]
     * @return [Flow]
     */
    @Query("SELECT * FROM ${Comment.TABLE_NAME}")
    fun getAllComments(): Flow<List<Comment>>
}