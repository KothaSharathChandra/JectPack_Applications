package com.example.jetweatherforecast.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NonNls

@Entity(tableName="fav_tbl")
data class Favorite(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name="city")
    val city:String,

    @ColumnInfo(name = "country")
    val country: String
)