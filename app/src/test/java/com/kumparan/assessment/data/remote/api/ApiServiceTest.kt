package com.kumparan.assessment.data.remote.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getAllPostsTest() = runBlocking {
        enqueueResponse("posts.json")
        val posts = service.getAllPosts().body()

        assertThat(posts).isNotNull()
        assertThat(posts!!.size).isEqualTo(100)
        assertThat(posts[0].userId).isEqualTo(1)
    }

    @Test
    fun getPostByIdTest() = runBlocking {
        enqueueResponse("post.json")
        val post = service.getPostById(1).body()

        assertThat(post).isNotNull()
        assertThat(post!!.id).isEqualTo(1)
        assertThat(post.userId).isEqualTo(1)
    }

    @Test
    fun getAllCommentsTest() = runBlocking {
        enqueueResponse("comments.json")
        val comments = service.getAllComments().body()

        assertThat(comments).isNotNull()
        assertThat(comments!!.size).isEqualTo(500)
        assertThat(comments[0].postId).isEqualTo(1)
    }

    @Test
    fun getCommentByPostIdTest() = runBlocking {
        enqueueResponse("postcomments.json")
        val comments = service.getCommentByPostId(1).body()

        assertThat(comments).isNotNull()
        assertThat(comments!!.size).isEqualTo(5)
        assertThat(comments[0].postId).isEqualTo(1)
    }

    @Test
    fun getCommentByIdTest() = runBlocking {
        enqueueResponse("comment.json")
        val comment = service.getCommentById(1).body()

        assertThat(comment).isNotNull()
        assertThat(comment!!.id).isEqualTo(1)
        assertThat(comment.name).isEqualTo("id labore ex et quam laborum")
    }

    @Test
    fun getAllAlbumsTest() = runBlocking {
        enqueueResponse("albums.json")
        val albums = service.getAllAlbums().body()

        assertThat(albums).isNotNull()
        assertThat(albums!!.size).isEqualTo(100)
        assertThat(albums[0].userId).isEqualTo(1)
    }

    @Test
    fun getAlbumByIdTest() = runBlocking {
        enqueueResponse("album.json")
        val album = service.getAlbumById(1).body()

        assertThat(album).isNotNull()
        assertThat(album!!.id).isEqualTo(1)
        assertThat(album.title).isEqualTo("quidem molestiae enim")
    }

    @Test
    fun getAllPhotosTest() = runBlocking {
        enqueueResponse("photos.json")
        val photos = service.getAllPhotos().body()

        assertThat(photos).isNotNull()
        assertThat(photos!!.size).isEqualTo(2)
        assertThat(photos[0].title).isEqualTo("accusamus beatae ad facilis cum similique qui sunt")
    }

    @Test
    fun getPhotoByIdTest() = runBlocking {
        enqueueResponse("photo.json")
        val photo = service.getPhotoById(1).body()

        assertThat(photo).isNotNull()
        assertThat(photo!!.id).isEqualTo(1)
        assertThat(photo.title).isEqualTo("accusamus beatae ad facilis cum similique qui sunt")
    }

    @Test
    fun getAlbumPhotosTest() = runBlocking {
        enqueueResponse("photos.json")
        val photos = service.getAlbumPhotos(1).body()

        assertThat(photos).isNotNull()
        assertThat(photos!!.size).isEqualTo(2)
        assertThat(photos[0].albumId).isEqualTo(1)
    }

    @Test
    fun getAllPhotosByAlbumIdTest() = runBlocking {
        enqueueResponse("photosalbum.json")
        val photos = service.getAllPhotosByAlbumId(1).body()

        assertThat(photos).isNotNull()
        assertThat(photos!!.size).isEqualTo(2)
        assertThat(photos[0].id).isEqualTo(201)
    }

    @Test
    fun getAllUsersTest() = runBlocking {
        enqueueResponse("users.json")
        val users = service.getAllUsers().body()

        assertThat(users).isNotNull()
        assertThat(users!!.size).isEqualTo(10)
        assertThat(users[0].name).isEqualTo("Leanne Graham")
    }

    @Test
    fun getUserAlbumsTest() = runBlocking {
        enqueueResponse("albums.json")
        val albums = service.getUserAlbums(1).body()

        assertThat(albums).isNotNull()
        assertThat(albums!!.size).isEqualTo(100)
        assertThat(albums[0].userId).isEqualTo(1)
    }

    @Test
    fun getUserByIdTest() = runBlocking {
        enqueueResponse("user.json")
        val user = service.getUserById(1).body()

        assertThat(user).isNotNull()
        assertThat(user!!.id).isEqualTo(1)
        assertThat(user.name).isEqualTo("Leanne Graham")
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}