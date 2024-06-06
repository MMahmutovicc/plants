package com.example.spirala

import com.google.gson.annotations.SerializedName

data class GetPlantsResponse(
    @SerializedName("data") val plants: List<PlantsItem>
)
