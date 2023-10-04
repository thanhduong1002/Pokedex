package com.example.pokedex.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")

data class Cart(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "foods")
    var listFoods: List<Food?>
)