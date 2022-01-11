package com.kumparan.assessment.ui.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kumparan.assessment.databinding.ItemAlbumBinding
import com.kumparan.assessment.databinding.ItemPhotoBinding
import com.kumparan.assessment.model.Photo
import com.kumparan.assessment.model.PhotoAlbum
import com.kumparan.assessment.ui.user.viewholder.AlbumViewHolder
import com.kumparan.assessment.ui.user.viewholder.PhotoViewHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PhotoListAdapter(private val context: Context, private val onItemClicked: (Photo) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var photoAlbum = arrayListOf<PhotoAlbum>()

    private val typeAlbum = 1
    private val typePhotos = 2

    fun addData(list: List<PhotoAlbum>) {
        photoAlbum.clear()
        photoAlbum.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) =
        if (photoAlbum[position].is_album) {
            typeAlbum
        } else {
            typePhotos
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == typeAlbum) {
            AlbumViewHolder(
                ItemAlbumBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            PhotoViewHolder(
                context,
                ItemPhotoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == typeAlbum) {
            val view = (holder as AlbumViewHolder)
            view.bind(photoAlbum[position].album)
        } else {
            val view = (holder as PhotoViewHolder)
            view.bind(photoAlbum[position].photo, onItemClicked)
        }
    }

    override fun getItemCount() = photoAlbum.size
}