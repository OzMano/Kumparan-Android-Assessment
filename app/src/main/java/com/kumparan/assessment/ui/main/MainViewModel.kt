package com.kumparan.assessment.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumparan.assessment.data.repository.PostRepository
import com.kumparan.assessment.data.repository.UserRepository
import com.kumparan.assessment.model.Post
import com.kumparan.assessment.model.State
import com.kumparan.assessment.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel untuk [MainActivity]
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _posts: MutableStateFlow<State<List<Post>>> = MutableStateFlow(State.loading())
    private val _users: MutableStateFlow<State<List<User>>> = MutableStateFlow(State.loading())

    val posts: StateFlow<State<List<Post>>> = _posts
    val users: StateFlow<State<List<User>>> = _users

    fun getPosts() {
        viewModelScope.launch {
            postRepository.getAllPosts()
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _posts.value = state }
        }
    }

    fun getAllUser() {
        viewModelScope.launch {
            userRepository.getAllUsers()
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _users.value = state }
        }
    }
}