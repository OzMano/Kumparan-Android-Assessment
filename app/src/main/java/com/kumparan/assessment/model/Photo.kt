package com.kumparan.assessment.model

/**
 * data class comment untuk serialization.
 */
data class Photo(
    var albumId: Int = 0,
    var id: Int = 0,
    var thumbnailUrl: String = "",
    var title: String = "",
    var url: String = ""
)