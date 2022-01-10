package com.kumparan.assessment.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * data class album untuk serialization.
 */
@Parcelize
@Entity(tableName = Album.TABLE_NAME)
data class Album(
    @PrimaryKey
    var id: Int = 0,
    var title: String = "",
    var userId: Int = 0
): Parcelable {
    companion object {
        const val TABLE_NAME = "albums"
    }
}