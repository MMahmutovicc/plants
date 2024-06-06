package com.example.spirala

import com.google.gson.annotations.SerializedName

data class PlantsItem(
    @SerializedName("id")               val id: Int,
    @SerializedName("scientific_name")  val sciName : String,
    @SerializedName("image_url")        val imageUrl: String,
)
