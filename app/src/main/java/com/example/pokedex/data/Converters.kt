package com.example.pokedex.data

import androidx.room.TypeConverter
import com.example.pokedex.models.Food
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromJson(json: String): List<Food> {
        val type = object : TypeToken<List<Food>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(list: List<Food>): String {
        return Gson().toJson(list)
    }
}