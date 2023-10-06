package com.example.pokedex.data.remote.models

import com.google.gson.annotations.SerializedName

data class Routes (

    @SerializedName("overview_polyline" ) var overviewPolyline : OverviewPolyline? = OverviewPolyline(),
    @SerializedName("summary"           ) var summary          : String?           = null,
    @SerializedName("warnings"          ) var warnings         : ArrayList<String> = arrayListOf(),
    @SerializedName("waypoint_order"    ) var waypointOrder    : ArrayList<String> = arrayListOf()

)