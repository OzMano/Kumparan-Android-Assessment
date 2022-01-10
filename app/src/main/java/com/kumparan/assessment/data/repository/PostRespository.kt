package com.kumparan.assessment.data.repository

import com.kumparan.assessment.data.local.dao.PostsDao
import com.kumparan.assessment.data.remote.api.ApiService
import com.kumparan.assessment.model.Post
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


interface PostRepository {
    fun getAllPosts(): Flow<Resource<List<Post>>>
    fun getPostById(postId: Int): Flow<Resource<Post>>
}

/**
 * Singleton repository untuk mengambil data post dan menyimpan ke local database
 */
@ExperimentalCoroutinesApi
class DefaultPostRespository @Inject constructor(
    private val postsDao: PostsDao,
    private val apiService: ApiService
): PostRepository {

    /**
     * mengambil list post dari semua user
     */
    override fun getAllPosts(): Flow<Resource<List<Post>>> {
        return flow<Resource<List<Post>>> {

            // Emit data dari local database terlebih dahulu
            emit(Resource.Success(postsDao.getAllPosts().first()))

            // mengambil list post dari semua user
            val apiResponse = apiService.getAllPosts()

            // parsing response body
            val remotePosts = apiResponse.body()

            // melakukan pengecekan response dari remote
            if (apiResponse.isSuccessful && remotePosts != null) {
                // simpan posts ke local database
                postsDao.addPosts(remotePosts)
            } else {
                // response error.
                emit(Resource.Failed(apiResponse.message()))
            }

            // emit data response dari local database
            postsDao.getAllPosts().map {
                emit(Resource.Success(it))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Resource.Failed("Error! Can't fetch posts data."))
        }
    }

    /**
     * mengambil post berdasarkan id
     */
    override fun getPostById(postId: Int): Flow<Resource<Post>> {
        return flow<Resource<Post>> {

            // Emit data dari local database terlebih dahulu
            emit(Resource.Success(postsDao.getPostById(postId).first()))

            // mengambil post berdasarkan id
            val apiResponse = apiService.getPostById(postId)

            // parsing response body
            val remotePost = apiResponse.body()

            // melakukan pengecekan response dari remote
            if (apiResponse.isSuccessful && remotePost != null) {
                // simpan post ke local database
                postsDao.addPosts(listOf(remotePost))
            } else {
                // response error.
                emit(Resource.Failed(apiResponse.message()))
            }

            // emit data response dari local database
            postsDao.getPostById(postId).map {
                emit(Resource.Success(it))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Resource.Failed("Error! Can't fetch post data."))
        }
    }
}