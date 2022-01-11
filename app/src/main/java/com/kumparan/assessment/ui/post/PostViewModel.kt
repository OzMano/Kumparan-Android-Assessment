package com.kumparan.assessment.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumparan.assessment.data.repository.CommentRepository
import com.kumparan.assessment.model.Comment
import com.kumparan.assessment.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel untuk [PostActivity]
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class PostViewModel @Inject constructor(
    private val commentRepository: CommentRepository
) : ViewModel() {

    private val _comments: MutableStateFlow<State<List<Comment>>> = MutableStateFlow(State.loading())

    val comments: StateFlow<State<List<Comment>>> = _comments

    fun getCommentsByPostId(postId: Int) {
        viewModelScope.launch {
            commentRepository.getCommentsByPostId(postId)
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _comments.value = state }
        }
    }
}