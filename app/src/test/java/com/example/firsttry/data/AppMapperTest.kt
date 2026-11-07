package com.example.firsttry.data

import com.example.firsttry.data.dto.AppDto
import com.example.firsttry.data.mapper.AppMapper
import org.junit.Assert.*
import org.junit.Test

class AppMapperTest {
    @Test fun mapsServerFieldsAndOptionalImage() {
        val dto = AppDto("42", "Название", "Описание", "Категория", "https://example.com/icon.png")
        val app = AppMapper().toDomain(dto)
        assertEquals(dto.id, app.id)
        assertEquals(dto.name, app.name)
        assertEquals(dto.description, app.description)
        assertEquals(dto.category, app.category)
        assertEquals(dto.iconUrl, app.iconUrl)
        assertNull(AppMapper().toDomain(dto.copy(iconUrl = null)).iconUrl)
    }

    @Test(expected = IllegalArgumentException::class)
    fun missingIdIsRejected() {
        AppMapper().toDomain(AppDto(name = "Название"))
    }
}
