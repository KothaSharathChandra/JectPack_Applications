package com.example.jetnoteapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jetnoteapp.model.Note
import java.util.concurrent.Flow

@Dao
interface NoteDatabaseDAO {
    @Query("SELECT * FROM notes_tbl")
    fun getNotes(): kotlinx.coroutines.flow.Flow<List<Note>>

    @Query("SELECT * FROM notes_tbl where id=:id")
    suspend fun getNoteById(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note:Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note:Note)

    @Query("DELETE from notes_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(note: Note)
}
