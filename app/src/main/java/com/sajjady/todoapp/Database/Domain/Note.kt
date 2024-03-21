package com.sajjady.todoapp.Database.Domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "priority") val priority: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "image") val image: String?
)