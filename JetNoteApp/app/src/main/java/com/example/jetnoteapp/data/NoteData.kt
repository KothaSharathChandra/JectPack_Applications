package com.example.jetnoteapp.data

import com.example.jetnoteapp.model.Note

class NotesDataSource{
    fun loadNotes(): List<Note>{
        return listOf(
        Note(title = "A good day" , description = "we went for a vacation "),
        Note(title = "JAVA" , description = "Used to develop WebApp,Mobile App... "),
        Note(title = "Web Technology" , description = "Used to develop Front End of APP "),
        Note(title = "SQL" , description = "Used to Store Data of End Users"),
        Note(title = "Kotlin" , description = "Used to develop Front End and Back End of App"),
        Note(title = "Linux" , description = "Used to Develop OS"),
        Note(title = "Cloud" , description = "Used to Store and Develop APP"),
        Note(title = "A good day" , description = "we went for a vacation "),
        Note(title = "A good day" , description = "we went for a vacation "))




    }
}