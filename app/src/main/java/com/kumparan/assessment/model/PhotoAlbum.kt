package com.kumparan.assessment.model

class PhotoAlbum(
    var is_album: Boolean = false,
    var album_title: String = "",
    var photo_title: String = "",
    var album: Album = Album(),
    var photo: Photo = Photo()
)