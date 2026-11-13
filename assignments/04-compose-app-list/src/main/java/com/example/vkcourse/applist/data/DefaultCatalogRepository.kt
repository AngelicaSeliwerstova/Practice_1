package com.example.vkcourse.applist.data

import com.example.vkcourse.applist.domain.model.CatalogApp
import com.example.vkcourse.applist.domain.repository.CatalogRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class DefaultCatalogRepository(
    private val dataSource: CatalogDataSource = CatalogDataSource(),
    private val api: CatalogApi = CatalogApi()
) : CatalogRepository {
    override fun getApps(): List<CatalogApp> =
        dataSource.getApps().map(CatalogAppDto::toDomain)

    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> =
        ids.associateWith { id -> api.getRating(id) }

    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> =
        coroutineScope {
            ids.map { id -> async { id to api.getRating(id) } }.awaitAll().toMap()
        }
}
