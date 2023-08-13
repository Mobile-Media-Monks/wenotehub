package com.example.wenotehub.domain.home

import com.example.wenotehub.data.model.NoteEntity
import com.example.wenotehub.data.model.NoteList


interface HomeRepository {
    suspend fun getAllNotes(): NoteList
    suspend fun createNote(note: NoteEntity)
}