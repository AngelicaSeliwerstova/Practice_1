package com.example.vkcourse.applist.domain.usecase

import com.example.vkcourse.applist.domain.model.CatalogApp
import com.example.vkcourse.applist.domain.model.CatalogAppIcon
import com.example.vkcourse.applist.domain.repository.CatalogRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertThrows
import org.junit.Test

class GetCatalogAppsUseCaseTest {
    private val first = CatalogApp("a", "Первое", "Описание A", "Категория A", CatalogAppIcon.SOCIAL)
    private val second = CatalogApp("b", "Второе", "Описание B", "Категория B", CatalogAppIcon.MUSIC)

    @Test
    fun returnsRepositoryAppsInOriginalOrder() {
        val apps = listOf(second, first)
        val useCase = GetCatalogAppsUseCase(FakeRepository(apps))

        assertEquals(listOf(second, first), useCase())
    }

    @Test
    fun returnsEmptyListWhenRepositoryIsEmpty() {
        assertEquals(emptyList<CatalogApp>(), GetCatalogAppsUseCase(FakeRepository(emptyList()))())
    }

    @Test
    fun invokesRepositoryExactlyOncePerCall() {
        val repository = FakeRepository(listOf(first))
        val useCase = GetCatalogAppsUseCase(repository)

        useCase()

        assertEquals(1, repository.getAppsCalls)
    }

    @Test
    fun readsCurrentRepositoryDataOnEachCall() {
        val repository = FakeRepository(listOf(first))
        val useCase = GetCatalogAppsUseCase(repository)

        assertEquals(listOf(first), useCase())
        repository.currentApps = listOf(second)
        assertSame(repository.currentApps, useCase())
        assertEquals(2, repository.getAppsCalls)
    }

    @Test
    fun propagatesRepositoryFailure() {
        val repository = FakeRepository(emptyList()).apply { failure = IllegalStateException("catalog unavailable") }

        val error = assertThrows(IllegalStateException::class.java) {
            GetCatalogAppsUseCase(repository)()
        }

        assertEquals("catalog unavailable", error.message)
    }

    private class FakeRepository(var currentApps: List<CatalogApp>) : CatalogRepository {
        var getAppsCalls = 0
        var failure: RuntimeException? = null

        override fun getApps(): List<CatalogApp> {
            getAppsCalls++
            failure?.let { throw it }
            return currentApps
        }

        override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> =
            error("Ratings are not used by this use case")

        override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> =
            error("Ratings are not used by this use case")
    }
}
