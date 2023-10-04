package com.example.pokedex.models

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Food")
data class Food (
    @PrimaryKey
    var name: String,

    @ColumnInfo(name = "imageFood")
    @DrawableRes val imageFood: Int,

    @ColumnInfo(name = "priceFood")
    var price: Double,

    @ColumnInfo(name = "quantity")
    var quantity: Int?

)

