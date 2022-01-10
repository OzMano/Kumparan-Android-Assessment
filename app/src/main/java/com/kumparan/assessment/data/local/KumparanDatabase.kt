package com.kumparan.assessment.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kumparan.assessment.data.local.dao.AlbumsDao
import com.kumparan.assessment.data.local.dao.CommentsDao
import com.kumparan.assessment.data.local.dao.PhotosDao
import com.kumparan.assessment.data.local.dao.PostsDao
import com.kumparan.assessment.model.*

/**
 * me'provide DAO
 * [PostsDao] menggunakan method [getPostsDao]
 * [PhotosDao] menggunakan method [getPhotosDao]
 * [CommentsDao] menggunakan method [getCommentsDao]
 * [AlbumsDao] menggunakan method [getAlbumsDao]
 */
@Database(
    entities = [
        Post::class, Photo::class, Comment::class, Album::class
    ],
    version = 1
)
abstract class KumparanDatabase : RoomDatabase() {

    /**
     * @return [PostsDao] data access object post
     */
    abstract fun getPostsDao(): PostsDao

    /**
     * @return [PhotosDao] data access object photo
     */
    abstract fun getPhotosDao(): PhotosDao

    /**
     * @return [CommentsDao] data access object comment
     */
    abstract fun getCommentsDao(): CommentsDao

    /**
     * @return [AlbumsDao] data access object album
     */
    abstract fun getAlbumsDao(): AlbumsDao

    companion object {
        const val DB_NAME = "kumparan_database"

        @Volatile
        private var INSTANCE: KumparanDatabase? = null

        fun getInstance(context: Context): KumparanDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KumparanDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}