package com.kumparan.assessment.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kumparan.assessment.model.Photo.Companion.TABLE_NAME
import kotlinx.parcelize.Parcelize

/**
 * data class comment untuk serialization.
 */
@Parcelize
@Entity(tableName = TABLE_NAME)
data class Photo(
    @PrimaryKey
    var id: Int = 0,
    var albumId: Int = 0,
    var thumbnailUrl: String = "",
    var title: String = "",
    var url: String = ""
): Parcelable {
    companion object {
        const val TABLE_NAME = "photos"
    }
}