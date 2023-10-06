package com.example.pokedex.data.repository

import android.util.Log
import com.example.pokedex.data.dao.CartDao
import com.example.pokedex.data.database.AppDatabase
import com.example.pokedex.data.remote.api.ServiceBuilder
import com.example.pokedex.data.remote.api.ServiceInterface
import com.example.pokedex.data.remote.models.Result
import com.example.pokedex.models.Cart
import com.example.pokedex.models.Food
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private val retrofit = ServiceBuilder.buildService(ServiceInterface::class.java)

    fun getDirection(origin: String, destination: String, apiKey: String) {
        retrofit.getDirection(origin, destination, "driving", apiKey).enqueue(object : Callback<Result> {
            override fun onResponse(
                call: Call<Result>,
                response: Response<Result>
            ) {
                try {
                    val responseBody = response.body()!!

                    Log.d("Test", "$responseBody")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
            }
        })
    }
}