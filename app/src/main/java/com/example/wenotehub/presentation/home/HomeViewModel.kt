package com.example.wenotehub.presentation.home

import androidx.lifecycle.ViewModel
import com.example.wenotehub.domain.home.HomeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import androidx.lifecycle.liveData
import com.example.wenotehub.core.Resource
import com.example.wenotehub.data.model.Note
import com.example.wenotehub.data.model.toNoteEntity


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl
) : ViewModel() {

    fun getAllNotes() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getAllNotes()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun createNote(note: Note) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.createNote(note.toNoteEntity())))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}