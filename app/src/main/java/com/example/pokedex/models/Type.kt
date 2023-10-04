package com.example.pokedex.models

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Type")
data class Type (
    @PrimaryKey
    var name: String,

    @ColumnInfo(name = "imageType")
    @DrawableRes val imageType: Int,
)