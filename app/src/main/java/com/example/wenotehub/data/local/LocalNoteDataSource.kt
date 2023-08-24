package com.example.wenotehub.data.local

import com.example.wenotehub.data.model.NoteEntity
import com.example.wenotehub.data.model.NoteList
import com.example.wenotehub.data.model.toNoteList
import javax.inject.Inject


class LocalNoteDataSource @Inject constructor(private val dao: NoteDao) {
    suspend fun getNotes(): NoteList = dao.getNotes().toNoteList()
    suspend fun addNote(note: NoteEntity) = dao.addNote(note)

    suspend fun updateNote(note: NoteEntity) = dao.updateNote(note)

    suspend fun deleteNote(note: NoteEntity) = dao.deleteNote(note)
}