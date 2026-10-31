package com.example.vkcourse.applist.domain.usecase

import com.example.vkcourse.applist.domain.model.CatalogApp
import com.example.vkcourse.applist.domain.repository.CatalogRepository

class GetCatalogAppsUseCase(
    private val repository: CatalogRepository
) {
    operator fun invoke(): List<CatalogApp> = repository.getApps()
}
