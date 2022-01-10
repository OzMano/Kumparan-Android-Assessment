package com.kumparan.assessment.data.repository

import com.kumparan.assessment.data.local.dao.AlbumsDao
import com.kumparan.assessment.data.remote.api.ApiService
import com.kumparan.assessment.model.Album
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


interface AlbumRepository {
    fun getAllAlbums(): Flow<Resource<List<Album>>>
    fun getUserAlbums(userId: Int): Flow<Resource<List<Album>>>
    fun getAlbumById(albumId: Int): Flow<Resource<Album>>
}

/**
 * Singleton repository untuk mengambil data album dan menyimpan ke local database
 */
@ExperimentalCoroutinesApi
class DefaultAlbumRespository @Inject constructor(
    private val albumsDao: AlbumsDao,
    private val apiService: ApiService
): AlbumRepository {

    /**
     * mengambil list album dari semua user
     */
    override fun getAllAlbums(): Flow<Resource<List<Album>>> {
        return flow<Resource<List<Album>>> {

            // Emit data dari local database terlebih dahulu
            emit(Resource.Success(albumsDao.getAllAlbums().first()))

            // mengambil list album dari semua user
            val apiResponse = apiService.getAllAlbums()

            // parsing response body
            val remoteAlbums = apiResponse.body()

            // melakukan pengecekan response dari remote
            if (apiResponse.isSuccessful && remoteAlbums != null) {
                // simpan albums ke local database
                albumsDao.addAlbums(remoteAlbums)
            } else {
                // response error.
                emit(Resource.Failed(apiResponse.message()))
            }

            // emit data response dari local database
            albumsDao.getAllAlbums().map {
                emit(Resource.Success(it))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Resource.Failed("Error! Can't fetch albums data."))
        }
    }

    /**
     * mengambil list album dari user berdasarkan user id
     */
    override fun getUserAlbums(userId: Int): Flow<Resource<List<Album>>> {
        return flow<Resource<List<Album>>> {

            // Emit data dari local database terlebih dahulu
            emit(Resource.Success(albumsDao.getAlbumsByUserId(userId).first()))

            // mengambil list album dari user
            val apiResponse = apiService.getUserAlbums(userId)

            // parsing response body
            val remoteAlbums = apiResponse.body()

            // melakukan pengecekan response dari remote
            if (apiResponse.isSuccessful && remoteAlbums != null) {
                // simpan albums ke local database
                albumsDao.addAlbums(remoteAlbums)
            } else {
                // response error.
                emit(Resource.Failed(apiResponse.message()))
            }

            // emit data response dari local database
            albumsDao.getAlbumsByUserId(userId).map {
                emit(Resource.Success(it))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Resource.Failed("Error! Can't fetch albums data."))
        }
    }

    /**
     * mengambil album berdasarkan id
     */
    override fun getAlbumById(albumId: Int): Flow<Resource<Album>> {
        return flow<Resource<Album>> {

            // Emit data dari local database terlebih dahulu
            emit(Resource.Success(albumsDao.getAlbumById(albumId).first()))

            // mengambil album berdasarkan id
            val apiResponse = apiService.getAlbumById(albumId)

            // parsing response body
            val remoteAlbum = apiResponse.body()

            // melakukan pengecekan response dari remote
            if (apiResponse.isSuccessful && remoteAlbum != null) {
                // simpan album ke local database
                albumsDao.addAlbums(listOf(remoteAlbum))
            } else {
                // response error.
                emit(Resource.Failed(apiResponse.message()))
            }

            // emit data response dari local database
            albumsDao.getAlbumById(albumId).map {
                emit(Resource.Success(it))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Resource.Failed("Error! Can't fetch album data."))
        }
    }
}