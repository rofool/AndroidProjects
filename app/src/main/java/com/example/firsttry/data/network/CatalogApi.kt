package com.example.firsttry.data.network

import com.example.firsttry.data.dto.AppDetailsDto
import com.example.firsttry.data.dto.AppDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {
    @GET("catalog")
    suspend fun getCatalog(): List<AppDto>

    @GET("{id}")
    suspend fun getAppDetails(@Path("id") id: String): AppDetailsDto

    @GET("catalog/{id}")
    suspend fun getCatalogAppDetails(@Path("id") id: String): AppDetailsDto
}
