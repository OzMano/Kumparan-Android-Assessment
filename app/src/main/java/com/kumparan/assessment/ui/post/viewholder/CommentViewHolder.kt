package com.kumparan.assessment.ui.post.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kumparan.assessment.databinding.ItemCommentBinding
import com.kumparan.assessment.model.Comment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CommentViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: Comment) {
        binding.commentAuthor.text = comment.name
        binding.commentBody.text = comment.body
    }
}