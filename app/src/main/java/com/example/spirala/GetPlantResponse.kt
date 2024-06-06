package com.example.spirala

import com.google.gson.annotations.SerializedName

data class GetPlantResponse (
    @SerializedName("data") val plant: Plant
)