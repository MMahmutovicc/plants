package com.example.spirala

import com.google.gson.annotations.SerializedName

data class Plant(
    @SerializedName("id")                       val id: Int,
    @SerializedName("common_name")              val name: String?,
    @SerializedName("scientific_name")          val latName: String,
    @SerializedName("family")                   val family: String,
    @SerializedName("edible")                   val edible: Boolean,
    @SerializedName("specifications")           val specifications: Specifications,
    @SerializedName("growth")                   val growth: Growth,
) {
    data class Specifications(
        @SerializedName("toxicity")             val toxicity: String?,
    )

    data class Growth(
        @SerializedName("soil_texture")         val soilTexture: Int?,
        @SerializedName("light")                val light: Int?,
        @SerializedName("atmospheric_humidity") val atmosphericHumidity: Int?,
    )
}
