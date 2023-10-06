package com.example.pokedex.data.remote.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.pokedex.data.remote.models.Result

interface ServiceInterface {
    //    @GET("maps/api/directions/json?{parameters}&key={apiKey}")
//    fun getDirection(@Path("parameters") parameters: String, @Path("apiKey") apiKey: String): Call<Result>
    @GET("maps/api/directions/json")
    fun getDirection(
        @retrofit2.http.Query("origin") origin: String,
        @retrofit2.http.Query("destination") destination: String,
        @retrofit2.http.Query("mode") mode: String,
        @retrofit2.http.Query("key") apiKey: String
    ): Call<Result>
}