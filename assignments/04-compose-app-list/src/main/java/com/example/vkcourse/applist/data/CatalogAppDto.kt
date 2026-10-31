package com.example.vkcourse.applist.data

import com.example.vkcourse.applist.domain.model.CatalogAppIcon

data class CatalogAppDto(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val icon: CatalogAppIcon
)
