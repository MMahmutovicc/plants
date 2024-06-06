package com.example.spirala

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("plants/search")
    suspend fun getPlants(
        @Query("q") q: String,
        @Query("token") trefleToken: String = BuildConfig.TREFLE_TOKEN
    ): Response<GetPlantsResponse>
    @GET("species/{id}")
    suspend fun getPlant(
        @Path("id") id: Int,
        @Query("token") trefleToken: String = BuildConfig.TREFLE_TOKEN
    ): Response<GetPlantResponse>
    @GET("plants/search")
    suspend fun getPlantsByFlowerColor(
        @Query("q") substr: String,
        @Query("filter[flower_color]") flowerColor: String,
        @Query("token") trefleToken: String = BuildConfig.TREFLE_TOKEN
    ): Response<GetPlantsResponse>
}