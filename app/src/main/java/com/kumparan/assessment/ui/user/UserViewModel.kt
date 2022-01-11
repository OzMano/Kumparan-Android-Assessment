package com.kumparan.assessment.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumparan.assessment.data.repository.AlbumRepository
import com.kumparan.assessment.data.repository.UserRepository
import com.kumparan.assessment.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel untuk [UserActivity]
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val albumRepository: AlbumRepository
) : ViewModel() {

    private val _user: MutableStateFlow<State<User>> = MutableStateFlow(State.loading())
    private val _albums: MutableStateFlow<State<List<PhotoAlbum>>> = MutableStateFlow(State.loading())

    val user: StateFlow<State<User>> = _user
    val albums: StateFlow<State<List<PhotoAlbum>>> = _albums

    fun getUserById(userId: Int) {
        viewModelScope.launch {
            userRepository.getUserById(userId)
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _user.value = state }
        }
    }

    fun getUserAlbum(userId: Int){
        viewModelScope.launch {
            albumRepository.getUserAlbumsPhotos(userId)
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _albums.value = state }
        }
    }
}