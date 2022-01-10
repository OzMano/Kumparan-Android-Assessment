package com.kumparan.assessment.data.repository

import com.kumparan.assessment.data.remote.api.ApiService
import com.kumparan.assessment.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


interface UserRepository {
    fun getAllUsers(): Flow<Resource<List<User>>>
    fun getUserById(userId: Int): Flow<Resource<User>>
}

/**
 * Singleton repository untuk mengambil data user
 */
@ExperimentalCoroutinesApi
class DefaultUserRespository @Inject constructor(
    private val apiService: ApiService
): UserRepository {

    /**
     * mengambil list user dari semua user
     */
    override fun getAllUsers(): Flow<Resource<List<User>>> {
        return flow<Resource<List<User>>> {

            // mengambil list user dari semua user
            val apiResponse = apiService.getAllUsers()

            // parsing response body
            val remoteUsers = apiResponse.body()

            // melakukan pengecekan response dari remote
            if (apiResponse.isSuccessful && remoteUsers != null) {
                // emit data response
                emit(Resource.Success(remoteUsers))
            } else {
                // response error.
                emit(Resource.Failed(apiResponse.message()))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Resource.Failed("Error! Can't fetch users data."))
        }
    }

    /**
     * mengambil user berdasarkan id
     */
    override fun getUserById(userId: Int): Flow<Resource<User>> {
        return flow<Resource<User>> {

            // mengambil user berdasarkan id
            val apiResponse = apiService.getUserById(userId)

            // parsing response body
            val remoteUser = apiResponse.body()

            // melakukan pengecekan response dari remote
            if (apiResponse.isSuccessful && remoteUser != null) {
                // emit data response
                emit(Resource.Success(remoteUser))
            } else {
                // response error.
                emit(Resource.Failed(apiResponse.message()))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Resource.Failed("Error! Can't fetch user data."))
        }
    }
}