package com.example.firsttry.domain.model

data class AppDetails(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String?,
    val developer: String,
    val ageRating: Int?,
    val size: Double?,
    val screenshotUrls: List<String>,
)
