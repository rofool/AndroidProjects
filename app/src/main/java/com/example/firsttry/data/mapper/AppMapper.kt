package com.example.firsttry.data.mapper

import com.example.firsttry.data.dto.AppDto
import com.example.firsttry.domain.model.AppIcon
import com.example.firsttry.domain.model.CatalogApp

class AppMapper {
    fun toDomain(dto: AppDto): CatalogApp = CatalogApp(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        category = dto.category,
        icon = when (dto.icon) {
            "chat" -> AppIcon.CHAT
            "music" -> AppIcon.MUSIC
            "book" -> AppIcon.BOOK
            "travel" -> AppIcon.TRAVEL
            "notes" -> AppIcon.NOTES
            "photo" -> AppIcon.PHOTO
            else -> AppIcon.DEFAULT
        },
    )
}
