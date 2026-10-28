package com.example.firsttry.di

import com.example.firsttry.data.local.LocalAppDataSource
import com.example.firsttry.data.mapper.AppMapper
import com.example.firsttry.data.repository.LocalAppRepository
import com.example.firsttry.domain.repository.AppRepository

class AppContainer {
    val appRepository: AppRepository = LocalAppRepository(LocalAppDataSource(), AppMapper())
}
