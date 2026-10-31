package com.example.vkcourse.applist.domain.model

data class CatalogApp(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val icon: CatalogAppIcon
)

enum class CatalogAppIcon {
    SOCIAL,
    MUSIC,
    VIDEO,
    MAIL,
    CLOUD,
    EDUCATION,
    GAMES,
    MARKET,
    CALLS
}

const val SHOWCASE_PAGE_SIZE = 3
const val SHOWCASE_PAGE_COUNT = 3
