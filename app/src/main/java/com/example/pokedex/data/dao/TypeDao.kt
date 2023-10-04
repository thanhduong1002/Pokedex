package com.example.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex.models.Type

@Dao
interface TypeDao {
    @Query("SELECT * FROM type")
    fun getAllTypes(): List<Type>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTypes(listTypes: List<Type>)
}