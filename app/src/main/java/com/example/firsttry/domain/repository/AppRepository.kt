package com.example.firsttry.domain.repository

import com.example.firsttry.domain.model.CatalogApp

interface AppRepository {
    suspend fun getApps(): List<CatalogApp>
}
