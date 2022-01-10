package com.kumparan.assessment.data.repository

import com.kumparan.assessment.data.local.dao.PhotosDao
import com.kumparan.assessment.data.remote.api.ApiService
import com.kumparan.assessment.model.Photo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


interface PhotoRepository {
    fun getAllPhotos(): Flow<Resource<List<Photo>>>
    fun getPhotosByAlbum(albumId: Int): Flow<Resource<List<Photo>>>
    fun getPhotoById(photoId: Int): Flow<Resource<Photo>>
}

/**
 * Singleton repository untuk mengambil data photo dan menyimpan ke local database
 */
@ExperimentalCoroutinesApi
class DefaultPhotoRespository @Inject constructor(
    private val photosDao: PhotosDao,
    private val apiService: ApiService
): PhotoRepository {

    /**
     * mengambil list photo dari semua user
     */
    override fun getAllPhotos(): Flow<Resource<List<Photo>>> {
        return flow<Resource<List<Photo>>> {

            // Emit data dari local database terlebih dahulu
            emit(Resource.Success(photosDao.getAllPhotos().first()))

            // mengambil list photo dari semua user
            val apiResponse = apiService.getAllPhotos()

            // parsing response body
            val remotePhotos = apiResponse.body()

            // melakukan pengecekan response dari remote
            if (apiResponse.isSuccessful && remotePhotos != null) {
                // simpan photos ke local database
                photosDao.addPhotos(remotePhotos)
            } else {
                // response error.
                emit(Resource.Failed(apiResponse.message()))
            }

            // emit data response dari local database
            photosDao.getAllPhotos().map {
                emit(Resource.Success(it))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Resource.Failed("Error! Can't fetch photos data."))
        }
    }

    /**
     * mengambil list photo dari album berdasarkan album id
     */
    override fun getPhotosByAlbum(albumId: Int): Flow<Resource<List<Photo>>> {
        return flow<Resource<List<Photo>>> {

            // Emit data dari local database terlebih dahulu
            emit(Resource.Success(photosDao.getPhotosByAlbumId(albumId).first()))

            // mengambil list photo dari album berdasarkan album id
            val apiResponse = apiService.getAllPhotosByAlbumId(albumId)

            // parsing response body
            val remotePhotos = apiResponse.body()

            // melakukan pengecekan response dari remote
            if (apiResponse.isSuccessful && remotePhotos != null) {
                // simpan photos ke local database
                photosDao.addPhotos(remotePhotos)
            } else {
                // response error.
                emit(Resource.Failed(apiResponse.message()))
            }

            // emit data response dari local database
            photosDao.getPhotosByAlbumId(albumId).map {
                emit(Resource.Success(it))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Resource.Failed("Error! Can't fetch photos data."))
        }
    }

    /**
     * mengambil photo berdasarkan id
     */
    override fun getPhotoById(photoId: Int): Flow<Resource<Photo>> {
        return flow<Resource<Photo>> {

            // Emit data dari local database terlebih dahulu
            emit(Resource.Success(photosDao.getPhotoById(photoId).first()))

            // mengambil photo berdasarkan id
            val apiResponse = apiService.getPhotoById(photoId)

            // parsing response body
            val remotePhoto = apiResponse.body()

            // melakukan pengecekan response dari remote
            if (apiResponse.isSuccessful && remotePhoto != null) {
                // simpan photo ke local database
                photosDao.addPhotos(listOf(remotePhoto))
            } else {
                // response error.
                emit(Resource.Failed(apiResponse.message()))
            }

            // emit data response dari local database
            photosDao.getPhotoById(photoId).map {
                emit(Resource.Success(it))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Resource.Failed("Error! Can't fetch photo data."))
        }
    }
}