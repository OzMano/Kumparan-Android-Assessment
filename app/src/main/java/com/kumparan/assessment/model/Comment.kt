package com.kumparan.assessment.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * data class comment untuk serialization.
 */
@Parcelize
@Entity(tableName = Comment.TABLE_NAME)
data class Comment(
    @PrimaryKey
    var id: Int = 0,
    var body: String = "",
    var email: String = "",
    var name: String = "",
    var postId: Int = 0
): Parcelable {
    companion object {
        const val TABLE_NAME = "comments"
    }
}