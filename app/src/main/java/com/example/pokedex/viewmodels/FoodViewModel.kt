package com.example.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.models.Food
import com.example.pokedex.data.repository.FoodRepository

class FoodViewModel(private var foodRepository: FoodRepository): ViewModel() {
    private val _foodList: MutableLiveData<List<Food>> = MutableLiveData(emptyList())
    val foodList: LiveData<List<Food>> = _foodList

    private val _updatedList: MutableLiveData<Boolean> = MutableLiveData()
    val updatedList: LiveData<Boolean> = _updatedList

    fun setFoodList(foods: List<Food>) {
        _foodList.value = foods
    }

    fun insertAll(listFoods: List<Food>) {
        _foodList.postValue(listFoods)
        foodRepository.insertFoods(listFoods)
    }

    fun getAllFoods() {
        val foodList: List<Food> = foodRepository.getAllFoods()
        _foodList.postValue(foodList)
    }
}