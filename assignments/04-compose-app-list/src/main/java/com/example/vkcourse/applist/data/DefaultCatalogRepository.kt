package com.example.vkcourse.applist.data

import com.example.vkcourse.applist.domain.model.CatalogApp
import com.example.vkcourse.applist.domain.repository.CatalogRepository

class DefaultCatalogRepository(
    private val dataSource: CatalogDataSource = CatalogDataSource()
) : CatalogRepository {
    override fun getApps(): List<CatalogApp> =
        dataSource.getApps().map(CatalogAppDto::toDomain)
}
