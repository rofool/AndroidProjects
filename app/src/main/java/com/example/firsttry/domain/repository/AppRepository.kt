package com.example.firsttry.domain.repository

import com.example.firsttry.domain.model.AppDetails
import com.example.firsttry.domain.model.CatalogApp

interface AppRepository {
    suspend fun getApps(): List<CatalogApp>
    suspend fun getAppDetails(id: String): AppDetails
}
