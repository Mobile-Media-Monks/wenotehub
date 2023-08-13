package com.example.wenotehub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Note(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val date: String = ""
)

data class NoteList(val results: List<Note> = listOf())

@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "date")
    val date: String = ""
)

fun List<NoteEntity>.toNoteList(): NoteList {
    val resultList = mutableListOf<Note>()
    this.forEach { noteEntity ->
        resultList.add(noteEntity.toNote())
    }
    return NoteList(resultList)
}


fun NoteEntity.toNote(): Note = Note(
    this.id,
    this.title,
    this.description,
    this.date
)


fun Note.toNoteEntity(): NoteEntity = NoteEntity(
    this.id,
    this.title,
    this.description,
    this.date
)