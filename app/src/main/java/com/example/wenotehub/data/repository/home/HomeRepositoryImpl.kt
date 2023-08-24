package com.example.wenotehub.data.repository.home

import com.example.wenotehub.core.Resource
import com.example.wenotehub.data.local.LocalNoteDataSource
import com.example.wenotehub.data.model.Note
import com.example.wenotehub.data.model.NoteEntity
import com.example.wenotehub.data.model.NoteList
import com.example.wenotehub.data.model.toNoteEntity
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val dataSourceLocal: LocalNoteDataSource) :
    HomeRepository {

    override suspend fun getNotes(): Resource<NoteList> {
        val response = try {
            dataSourceLocal.getNotes()
        } catch (e: Exception){
            return Resource.Failure(e)
        }
        return Resource.Success(response)
    }

    override suspend fun addNote(note: Note) = dataSourceLocal.addNote(note.toNoteEntity())

    override suspend fun updateNote(note: Note) = dataSourceLocal.updateNote(note.toNoteEntity())

    override suspend fun deleteNote(note: Note) = dataSourceLocal.deleteNote(note.toNoteEntity())
}