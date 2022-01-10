package com.kumparan.assessment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kumparan.assessment.model.Post
import kotlinx.coroutines.flow.Flow

/**
 * dao untuk [com.kumparan.assessment.data.local.KumparanDatabase]
 */
@Dao
interface PostsDao {

    /**
     * menambahkan [posts] ke table [Post.TABLE_NAME]
     * nilai yang sama pada table akan otomatis di replace
     * @param posts typicode posts
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPosts(posts: List<Post>)

    /**
     * menghapus semua post dari table [Post.TABLE_NAME]
     */
    @Query("DELETE FROM ${Post.TABLE_NAME}")
    suspend fun deleteAllPosts()

    /**
     * mengambil post dari table [Post.TABLE_NAME] jika id nya sama dengan [postId]
     * @param postId post id dari [Post]
     */
    @Query("SELECT * FROM ${Post.TABLE_NAME} WHERE ID = :postId")
    fun getPostById(postId: Int): Flow<Post>

    /**
     * mengambil semua post dari table [Post.TABLE_NAME]
     * @return [Flow]
     */
    @Query("SELECT * FROM ${Post.TABLE_NAME}")
    fun getAllPosts(): Flow<List<Post>>
}