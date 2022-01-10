package com.kumparan.assessment.ui.main.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.kumparan.assessment.databinding.ItemPostBinding
import com.kumparan.assessment.model.Post
import com.kumparan.assessment.model.User
import com.kumparan.assessment.ui.main.MainActivity.Companion.userList
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post, onItemClicked: (Post, User) -> Unit) {
        var author = User()

        userList.forEach {
            if (it.id == post.userId) author = it
        }

        binding.postTitle.text = post.title
        binding.postBody.text = post.body
        binding.postUserName.text = author.name
        binding.postUserCompany.text = author.company.name

        binding.root.setOnClickListener {
            onItemClicked(post, author)
        }
    }
}