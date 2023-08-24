package com.example.wenotehub.data.repository.home

import com.example.wenotehub.core.Resource
import com.example.wenotehub.data.model.Note
import com.example.wenotehub.data.model.NoteEntity
import com.example.wenotehub.data.model.NoteList


interface HomeRepository {
    suspend fun getNotes(): Resource<NoteList>
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
}