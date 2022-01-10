package com.kumparan.assessment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kumparan.assessment.model.Album
import kotlinx.coroutines.flow.Flow

/**
 * dao untuk [com.kumparan.assessment.data.local.KumparanDatabase]
 */
@Dao
interface AlbumsDao {

    /**
     * menambahkan [albums] ke table [Album.TABLE_NAME]
     * nilai yang sama pada table akan otomatis di replace
     * @param albums typicode albums
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlbums(albums: List<Album>)

    /**
     * menghapus semua album dari table [Album.TABLE_NAME]
     */
    @Query("DELETE FROM ${Album.TABLE_NAME}")
    suspend fun deleteAllAlbums()

    /**
     * mengambil album dari table [Album.TABLE_NAME] jika id nya sama dengan [albumId]
     * @param albumId album id dari [Album]
     */
    @Query("SELECT * FROM ${Album.TABLE_NAME} WHERE ID = :albumId")
    fun getAlbumById(albumId: Int): Flow<Album>

    /**
     * mengambil list album dari table [Album.TABLE_NAME] jika userid nya sama dengan [userId]
     * @param userId user id
     */
    @Query("SELECT * FROM ${Album.TABLE_NAME} WHERE USERID = :userId")
    fun getAlbumsByUserId(userId: Int): Flow<List<Album>>

    /**
     * mengambil semua album dari table [Album.TABLE_NAME]
     * @return [Flow]
     */
    @Query("SELECT * FROM ${Album.TABLE_NAME}")
    fun getAllAlbums(): Flow<List<Album>>
}