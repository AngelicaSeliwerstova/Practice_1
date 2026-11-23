package com.example.vkcourse.applist.data

import kotlinx.coroutines.delay

/** Локальная имитация запроса к серверу рейтингов. */
interface RatingApi {
    suspend fun getRating(id: String): Float
}

class CatalogApi : RatingApi {
    override suspend fun getRating(id: String): Float {
        require(id.isNotBlank())
        delay(1_500)
        return (30..50).random() / 10f
    }
}
