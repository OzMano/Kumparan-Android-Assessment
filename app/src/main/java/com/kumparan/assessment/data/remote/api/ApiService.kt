package com.kumparan.assessment.data.remote.api

import com.kumparan.assessment.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * service api untuk post, comments, albums, photos, todos, users
 * dari https://jsonplaceholder.typicode.com/
 */
interface ApiService {

    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @GET("/posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: Int): Response<Post>

    @GET("/comments")
    suspend fun getAllComments(): Response<List<Comment>>

    @GET("/comments/{id}")
    suspend fun getCommentById(@Path("id") id: Int): Response<Comment>

    @GET("/albums")
    suspend fun getAllAlbums(): Response<List<Album>>

    @GET("/albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int): Response<Album>

    @GET("/photos")
    suspend fun getAllPhotos(): Response<List<Photo>>

    @GET("/photos/{id}")
    suspend fun getPhotoById(@Path("id") id: Int): Response<Photo>

    @GET("/photos")
    suspend fun getAllPhotosByAlbumId(@Query("albumId") albumId: Int): Response<List<Photo>>

    @GET("/users")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("/users/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>

    companion object {
        const val TYPICODE_API_URL = "https://jsonplaceholder.typicode.com"
    }
}