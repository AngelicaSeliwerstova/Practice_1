package com.example.vkcourse.applist.data

import com.example.vkcourse.applist.domain.model.CatalogAppIcon
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class DefaultCatalogRepositoryTest {
    private val first = CatalogAppDto("a", "Первое", "Описание A", "Категория A", CatalogAppIcon.SOCIAL)
    private val second = CatalogAppDto("b", "Второе", "Описание B", "Категория B", CatalogAppIcon.MUSIC)

    @Test
    fun getAppsMapsAllItemsInSourceOrder() {
        val repository = repositoryWith(listOf(second, first))

        assertEquals(listOf(second.toDomain(), first.toDomain()), repository.getApps())
    }

    @Test
    fun getAppsReturnsEmptyListForEmptySource() {
        assertEquals(emptyList<Any>(), repositoryWith(emptyList()).getApps())
    }

    @Test
    fun sequentialRatingsVisitEveryIdInOrder() = runBlocking {
        val api = FakeRatingApi(mapOf("a" to 3.5f, "b" to 4.8f))
        val repository = repositoryWith(api = api)

        assertEquals(mapOf("b" to 4.8f, "a" to 3.5f), repository.loadRatingsSequentially(listOf("b", "a")))
        assertEquals(listOf("b", "a"), api.requestedIds)
    }

    @Test
    fun parallelRatingsReturnValuesForAllIdsInInputOrder() = runBlocking {
        val api = FakeRatingApi(mapOf("a" to 3.5f, "b" to 4.8f))
        val repository = repositoryWith(api = api)

        val ratings = repository.loadRatingsInParallel(listOf("b", "a"))

        assertEquals(listOf("b", "a"), ratings.keys.toList())
        assertEquals(mapOf("b" to 4.8f, "a" to 3.5f), ratings)
        assertEquals(listOf("b", "a"), api.requestedIds)
    }

    @Test
    fun emptyIdsDoNotCallRatingApi() = runBlocking {
        val api = FakeRatingApi(emptyMap())
        val repository = repositoryWith(api = api)

        assertEquals(emptyMap<String, Float>(), repository.loadRatingsSequentially(emptyList()))
        assertEquals(emptyMap<String, Float>(), repository.loadRatingsInParallel(emptyList()))
        assertEquals(emptyList<String>(), api.requestedIds)
    }

    @Test
    fun ratingFailuresArePropagatedByBothStrategies() {
        val repository = repositoryWith(api = FakeRatingApi(emptyMap()))

        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { repository.loadRatingsSequentially(listOf("missing")) }
        }
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { repository.loadRatingsInParallel(listOf("missing")) }
        }
    }

    private fun repositoryWith(
        dtos: List<CatalogAppDto> = listOf(first, second),
        api: RatingApi = FakeRatingApi(emptyMap())
    ): DefaultCatalogRepository = DefaultCatalogRepository(
        dataSource = object : CatalogAppDataSource {
            override fun getApps(): List<CatalogAppDto> = dtos
        },
        api = api
    )

    private class FakeRatingApi(private val ratings: Map<String, Float>) : RatingApi {
        val requestedIds = mutableListOf<String>()

        override suspend fun getRating(id: String): Float {
            requestedIds += id
            return requireNotNull(ratings[id]) { "Missing rating for $id" }
        }
    }
}
