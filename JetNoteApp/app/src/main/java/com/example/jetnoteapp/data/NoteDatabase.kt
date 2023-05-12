package com.example.jetnoteapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.jetnoteapp.model.Note
import com.example.jetnoteapp.util.DateConverter
import com.example.jetnoteapp.util.UUIDConvertor

@Database(entities = [Note ::class], version=1, exportSchema = false)
@TypeConverters(DateConverter::class,UUIDConvertor::class)
abstract class NoteDatabase: RoomDatabase() {
         abstract fun noteDao(): NoteDatabaseDAO
}