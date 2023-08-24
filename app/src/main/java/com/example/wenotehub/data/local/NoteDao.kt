package com.example.wenotehub.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wenotehub.data.model.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM noteentity")
    suspend fun getNotes(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(item: NoteEntity)

    @Update
    suspend fun updateNote(item: NoteEntity)

    @Delete
    suspend fun deleteNote(item: NoteEntity)
}