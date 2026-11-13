package com.example.vkcourse.applist.domain.repository

import com.example.vkcourse.applist.domain.model.CatalogApp

interface CatalogRepository {
    fun getApps(): List<CatalogApp>
    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float>
    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float>
}
