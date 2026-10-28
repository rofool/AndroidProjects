package com.example.firsttry.data

import com.example.firsttry.data.dto.AppDto
import com.example.firsttry.data.mapper.AppMapper
import com.example.firsttry.domain.model.AppIcon
import org.junit.Assert.assertEquals
import org.junit.Test

class AppMapperTest {
    private val mapper = AppMapper()

    @Test fun mapsEveryField() {
        val dto = AppDto("42", "Название", "Описание", "Категория", "photo")
        val app = mapper.toDomain(dto)
        assertEquals(dto.id, app.id)
        assertEquals(dto.name, app.name)
        assertEquals(dto.description, app.description)
        assertEquals(dto.category, app.category)
        assertEquals(AppIcon.PHOTO, app.icon)
    }

    @Test fun unknownIconUsesDefault() {
        val dto = AppDto("42", "Название", "Описание", "Категория", "missing-icon")
        assertEquals(AppIcon.DEFAULT, mapper.toDomain(dto).icon)
    }
}
