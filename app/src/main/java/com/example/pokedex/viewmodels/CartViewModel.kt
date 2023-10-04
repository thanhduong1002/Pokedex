package com.example.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.models.Cart
import com.example.pokedex.models.Food
import com.example.pokedex.data.repository.CartRepository

class CartViewModel(private var cartRepository: CartRepository): ViewModel() {
    private val _foodList: MutableLiveData<List<Food>> = MutableLiveData(emptyList())
    val foodList: LiveData<List<Food>> = _foodList

    val _totalPrice: MutableLiveData<Double> = MutableLiveData()
    val totalPrice: LiveData<Double> = _totalPrice

    fun insertCart(cart: Cart) {
        cartRepository.insertCart(cart)
    }

    suspend fun getCartHaveId1() {
        val cartId1 : Cart = cartRepository.getCartHaveId1()
        _foodList.postValue(cartId1.listFoods as List<Food>?)
    }

    suspend fun getCartById1(): Cart = cartRepository.getCartHaveId1()

    fun updateListFoods(listFoods: List<Food>) {
        cartRepository.updateListFoods(listFoods)
    }
}