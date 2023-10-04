package com.example.pokedex.data.repository

import com.example.pokedex.data.dao.CartDao
import com.example.pokedex.data.database.AppDatabase
import com.example.pokedex.models.Cart
import com.example.pokedex.models.Food
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartRepository constructor(
    private val cartDao: CartDao,
    private val database: AppDatabase
) {
    fun insertCart(cart: Cart) {
        CoroutineScope(Dispatchers.IO).launch {
            database.cartDao().insert(cart)
        }
    }

    suspend fun getCartHaveId1(): Cart {
        return withContext(Dispatchers.IO) {
            cartDao.getCartHaveId1()
        }
    }

    fun updateListFoods(listFoods: List<Food>) {
        cartDao.updateListFoods(listFoods)
    }
}