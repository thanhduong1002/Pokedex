package com.example.pokedex.data.repository

import com.example.pokedex.data.dao.FoodDao
import com.example.pokedex.data.database.AppDatabase
import com.example.pokedex.models.Food
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodRepository constructor(
    private val foodDao: FoodDao,
    private val database: AppDatabase
) {
    fun insertFoods(listFoods: List<Food>) {
        CoroutineScope(Dispatchers.IO).launch {
            database.foodDao().insertAll(listFoods)
        }
    }

    fun getAllFoods(): List<Food> = foodDao.getAllFoods()
}