package com.example.wenotehub.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wenotehub.data.model.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM noteentity")
    suspend fun getAllNotes(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note: NoteEntity)
}