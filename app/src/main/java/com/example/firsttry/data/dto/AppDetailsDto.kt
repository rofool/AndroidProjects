package com.example.firsttry.data.dto

import com.google.gson.annotations.SerializedName

data class AppDetailsDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val category: String? = null,
    val iconUrl: String? = null,
    val developer: String? = null,
    val ageRating: Int? = null,
    val size: Double? = null,
    @SerializedName(value = "screenshotUrlList", alternate = ["screenshots"])
    val screenshotUrlList: List<String>? = null,
)
