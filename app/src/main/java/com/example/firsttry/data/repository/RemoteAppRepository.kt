package com.example.firsttry.data.repository

import com.example.firsttry.data.mapper.AppDetailsMapper
import com.example.firsttry.data.mapper.AppMapper
import com.example.firsttry.data.network.CatalogApi
import com.example.firsttry.domain.model.AppDetails
import com.example.firsttry.domain.model.CatalogApp
import com.example.firsttry.domain.repository.AppRepository
import retrofit2.HttpException

class RemoteAppRepository(
    private val api: CatalogApi,
    private val appMapper: AppMapper,
    private val detailsMapper: AppDetailsMapper,
) : AppRepository {
    override suspend fun getApps(): List<CatalogApp> = api.getCatalog().map(appMapper::toDomain)

    override suspend fun getAppDetails(id: String): AppDetails {
        require(id.isNotBlank())
        val dto = try {
            api.getAppDetails(id)
        } catch (exception: HttpException) {
            if (exception.code() != 404) throw exception
            api.getCatalogAppDetails(id)
        }
        return detailsMapper.toDomain(dto)
    }
}
