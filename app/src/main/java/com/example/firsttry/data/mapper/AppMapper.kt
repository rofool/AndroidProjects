package com.example.firsttry.data.mapper

import com.example.firsttry.data.dto.AppDto
import com.example.firsttry.domain.model.CatalogApp

class AppMapper {
    fun toDomain(dto: AppDto): CatalogApp = CatalogApp(
        id = requireNotNull(dto.id).also { require(it.isNotBlank()) },
        name = requireNotNull(dto.name).also { require(it.isNotBlank()) },
        description = dto.description.orEmpty(),
        category = dto.category.orEmpty(),
        iconUrl = dto.iconUrl?.takeIf { it.isNotBlank() },
    )
}
