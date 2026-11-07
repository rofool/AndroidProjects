package com.example.firsttry.di

import com.example.firsttry.data.mapper.AppDetailsMapper
import com.example.firsttry.data.mapper.AppMapper
import com.example.firsttry.data.network.ApiFactory
import com.example.firsttry.data.repository.RemoteAppRepository
import com.example.firsttry.domain.repository.AppRepository

class AppContainer {
    val appRepository: AppRepository = RemoteAppRepository(
        ApiFactory.create(), AppMapper(), AppDetailsMapper(),
    )
}
