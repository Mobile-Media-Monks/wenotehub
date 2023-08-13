package com.example.wenotehub.domain.home

import com.example.wenotehub.core.Resource
import com.example.wenotehub.data.local.LocalNoteDataSource
import com.example.wenotehub.data.model.NoteEntity
import com.example.wenotehub.data.model.NoteList
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val dataSourceLocal: LocalNoteDataSource) :
    HomeRepository {

    override suspend fun getAllNotes(): Resource<NoteList> {
        val response = try {
            dataSourceLocal.getAllNotes()
        } catch (e: Exception){
            return Resource.Failure(e)
        }
        return Resource.Success(response)
    }

    override suspend fun createNote(note: NoteEntity) = dataSourceLocal.saveNote(note)
}