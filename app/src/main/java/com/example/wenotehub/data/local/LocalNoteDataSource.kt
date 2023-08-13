package com.example.wenotehub.data.local

import com.example.wenotehub.data.model.NoteEntity
import com.example.wenotehub.data.model.NoteList
import com.example.wenotehub.data.model.toNoteList
import javax.inject.Inject


class LocalNoteDataSource @Inject constructor(private val dao: NoteDao) {
    suspend fun getAllNotes(): NoteList = dao.getAllNotes().toNoteList()
    suspend fun saveNote(note: NoteEntity) = dao.saveNote(note)
}