package com.example.pokedex.data.remote.models

import com.google.gson.annotations.SerializedName


data class OverviewPolyline (
    @SerializedName("points" ) var points : String? = null
)