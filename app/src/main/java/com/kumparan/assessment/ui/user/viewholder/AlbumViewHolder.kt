package com.kumparan.assessment.ui.user.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kumparan.assessment.databinding.ItemAlbumBinding
import com.kumparan.assessment.model.Album
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AlbumViewHolder(private val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(album: Album) {
        binding.albumTitle.text = album.title
    }
}