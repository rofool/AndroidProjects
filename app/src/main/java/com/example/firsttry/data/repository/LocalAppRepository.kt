package com.example.firsttry.data.repository

import com.example.firsttry.data.local.LocalAppDataSource
import com.example.firsttry.data.mapper.AppMapper
import com.example.firsttry.domain.model.CatalogApp
import com.example.firsttry.domain.repository.AppRepository

class LocalAppRepository(
    private val dataSource: LocalAppDataSource,
    private val mapper: AppMapper,
) : AppRepository {
    override suspend fun getApps(): List<CatalogApp> = dataSource.getApps().map(mapper::toDomain)
}
