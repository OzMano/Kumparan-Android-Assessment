package com.kumparan.assessment.data.repository

import com.kumparan.assessment.data.local.dao.CommentsDao
import com.kumparan.assessment.data.remote.api.ApiService
import com.kumparan.assessment.model.Comment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


interface CommentRepository {
    fun getCommentsByPostId(posiId: Int): Flow<Resource<List<Comment>>>
}

/**
 * Singleton repository untuk mengambil data comment dan menyimpan ke local database
 */
@ExperimentalCoroutinesApi
class DefaultCommentRespository @Inject constructor(
    private val commentsDao: CommentsDao,
    private val apiService: ApiService
): CommentRepository {

    /**
     * mengambil list comment dari post berdasarkan post id
     */
    override fun getCommentsByPostId(posiId: Int): Flow<Resource<List<Comment>>> {
        return flow<Resource<List<Comment>>> {

            // Emit data dari local database terlebih dahulu
            emit(Resource.Success(commentsDao.getCommentByPostId(posiId).first()))

            // mengambil list comment berdasarkan post id
            val apiResponse = apiService.getCommentByPostId(posiId)

            // parsing response body
            val remoteComments = apiResponse.body()

            // melakukan pengecekan response dari remote
            if (apiResponse.isSuccessful && remoteComments != null) {
                // simpan comments ke local database
                commentsDao.addComments(remoteComments)
            } else {
                // response error.
                emit(Resource.Failed(apiResponse.message()))
            }

            // emit data response dari local database
            emit(Resource.Success(commentsDao.getCommentByPostId(posiId).first()))
        }.catch { e ->
            e.printStackTrace()
            emit(Resource.Failed("Error! Can't fetch comments data."))
        }
    }
}