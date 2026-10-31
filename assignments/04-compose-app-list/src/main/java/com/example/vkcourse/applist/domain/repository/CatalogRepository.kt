package com.example.vkcourse.applist.domain.repository

import com.example.vkcourse.applist.domain.model.CatalogApp

interface CatalogRepository {
    fun getApps(): List<CatalogApp>
}
