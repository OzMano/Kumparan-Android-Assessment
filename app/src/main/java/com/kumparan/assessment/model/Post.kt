package com.kumparan.assessment.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * data class post untuk serialization.
 */
@Parcelize
@Entity(tableName = Post.TABLE_NAME)
data class Post(
    @PrimaryKey
    var id: Int = 0,
    var body: String = "",
    var title: String = "",
    var userId: Int = 0
): Parcelable {
    companion object {
        const val TABLE_NAME = "posts"
    }
}