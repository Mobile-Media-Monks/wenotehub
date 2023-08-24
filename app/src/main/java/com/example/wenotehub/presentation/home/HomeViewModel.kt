package com.example.wenotehub.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wenotehub.data.repository.home.HomeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.wenotehub.core.Resource
import com.example.wenotehub.data.model.Note
import kotlinx.coroutines.launch


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepositoryImpl,
) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>(listOf())
    val notes: LiveData<List<Note>> = _notes



    init {
        viewModelScope.launch {
            when(val result = repo.getNotes()){
                is Resource.Success -> {
                    _notes.value = result.data.results
                }
                is Resource.Failure -> {
                    Log.d("EPPAAAA", "Failure")
                }
                is Resource.Loading -> {
                    Log.d("EPPAAAA", "Loading")
                }
            }
        }
    }

    fun onCreateNote(note: Note)  {
        viewModelScope.launch {
            Log.d("EPPAAAA", "Creando $note")
            repo.addNote(note)
        }
    }

    fun onCreateBackup(){
    }
}