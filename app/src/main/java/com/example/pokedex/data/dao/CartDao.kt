package com.example.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokedex.models.Cart
import com.example.pokedex.models.Food

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAllCarts(): List<Cart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: Cart)

    @Query("SELECT * FROM cart WHERE id = 1")
    fun getCartHaveId1(): Cart

    @Query("UPDATE Cart SET foods = :newFoods WHERE id = 1")
    fun updateListFoods(newFoods: List<Food>)
}