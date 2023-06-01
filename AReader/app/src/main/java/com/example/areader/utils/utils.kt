package com.example.areader.utils


import com.google.firebase.Timestamp
import java.text.DateFormat

fun formatDate(timestamp: Timestamp): String{
    val date=DateFormat.getDateInstance()
        .format(timestamp.toDate())
        .toString().split(",")[0] //March 12
    return date
}
