package com.example.firsttry.data

import com.example.firsttry.data.local.LocalAppDataSource
import com.example.firsttry.data.mapper.AppMapper
import com.example.firsttry.data.repository.LocalAppRepository
import com.example.firsttry.domain.model.AppIcon
import com.example.firsttry.domain.repository.AppRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LocalAppRepositoryTest {
    @Test fun returnsCompleteCatalogWithUniqueIdsAndMappedIcons() = runTest {
        val repository: AppRepository = LocalAppRepository(LocalAppDataSource(), AppMapper())
        val apps = repository.getApps()
        assertEquals(8, apps.size)
        assertEquals(apps.size, apps.map { it.id }.distinct().size)
        assertEquals("Диалог", apps.first().name)
        assertEquals("На волне", apps.last().name)
        assertTrue(apps.all { it.icon != AppIcon.DEFAULT })
        assertTrue(apps.all { it.description.isNotBlank() && it.category.isNotBlank() })
    }
}
