package com.sajjady.todoapp.Database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sajjady.todoapp.Database.Domain.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    fun getAll(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(note: Note)

    @Delete
    suspend fun delete(item: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: Note)

}