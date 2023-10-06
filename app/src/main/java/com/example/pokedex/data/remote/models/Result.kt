package com.example.pokedex.data.remote.models

import com.google.gson.annotations.SerializedName

data class Result (
    @SerializedName("routes"             ) var routes            : ArrayList<Routes>            = arrayListOf(),
    @SerializedName("status"             ) var status            : String?                      = null
)
