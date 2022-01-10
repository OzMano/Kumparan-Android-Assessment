package com.kumparan.assessment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kumparan.assessment.model.Photo
import kotlinx.coroutines.flow.Flow

/**
 * dao untuk [com.kumparan.assessment.data.local.KumparanDatabase]
 */
@Dao
interface PhotosDao {

    /**
     * menambahkan [photos] ke table [Photo.TABLE_NAME]
     * nilai yang sama pada table akan otomatis di replace
     * @param photos typicode photos
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhotos(photos: List<Photo>)

    /**
     * menghapus semua photo dari table [Photo.TABLE_NAME]
     */
    @Query("DELETE FROM ${Photo.TABLE_NAME}")
    suspend fun deleteAllPhotos()

    /**
     * mengambil photo dari table [Photo.TABLE_NAME] jika id nya sama dengan [photoId]
     * @param photoId photo id dari [Photo]
     */
    @Query("SELECT * FROM ${Photo.TABLE_NAME} WHERE ID = :photoId")
    fun getPhotoById(photoId: Int): Flow<Photo>

    /**
     * mengambil list photo dari table [Photo.TABLE_NAME] jika album id nya sama dengan [albumId]
     * @param albumId album id
     */
    @Query("SELECT * FROM ${Photo.TABLE_NAME} WHERE ID = :albumId")
    fun getPhotosByAlbumId(albumId: Int): Flow<List<Photo>>

    /**
     * mengambil semua photo dari table [Photo.TABLE_NAME]
     * @return [Flow]
     */
    @Query("SELECT * FROM ${Photo.TABLE_NAME}")
    fun getAllPhotos(): Flow<List<Photo>>
}