package com.kumparan.assessment.model

/**
 * data class post untuk serialization.
 */
data class Post(
    var body: String = "",
    var id: Int = 0,
    var title: String = "",
    var userId: Int = 0
)