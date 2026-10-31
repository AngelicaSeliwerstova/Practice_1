package com.example.vkcourse.applist.data

import com.example.vkcourse.applist.domain.model.CatalogApp

fun CatalogAppDto.toDomain(): CatalogApp = CatalogApp(
    id = id,
    name = name,
    description = description,
    category = category,
    icon = icon
)
