package com.example.firsttry.data.mapper

import com.example.firsttry.data.dto.AppDetailsDto
import com.example.firsttry.domain.model.AppDetails

class AppDetailsMapper {
    fun toDomain(dto: AppDetailsDto): AppDetails = AppDetails(
        id = requireNotNull(dto.id).also { require(it.isNotBlank()) },
        name = requireNotNull(dto.name).also { require(it.isNotBlank()) },
        description = dto.description.orEmpty(),
        category = dto.category.orEmpty(),
        iconUrl = dto.iconUrl?.takeIf { it.isNotBlank() },
        developer = dto.developer.orEmpty(),
        ageRating = dto.ageRating,
        size = dto.size,
        screenshotUrls = dto.screenshotUrlList.orEmpty(),
    )
}
