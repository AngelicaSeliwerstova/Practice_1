package com.example.vkcourse.applist.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.system.measureTimeMillis

class CatalogRatingsTest {
    @Test
    fun bothStrategiesLoadRatingsForTwelveApps() = runBlocking {
        val repository = DefaultCatalogRepository()
        val ids = repository.getApps().map { it.id }
        assertTrue(ids.size >= 10)

        var sequential = emptyMap<String, Float>()
        val sequentialMs = measureTimeMillis {
            sequential = repository.loadRatingsSequentially(ids)
        }
        var parallel = emptyMap<String, Float>()
        val parallelMs = measureTimeMillis {
            parallel = repository.loadRatingsInParallel(ids)
        }

        assertEquals(ids.toSet(), sequential.keys)
        assertEquals(ids.toSet(), parallel.keys)
        assertTrue(sequential.values.all { it in 3.0f..5.0f })
        assertTrue(parallel.values.all { it in 3.0f..5.0f })
        assertTrue("Параллельная загрузка должна быть быстрее", parallelMs < sequentialMs)
        println("Ratings benchmark (${ids.size} apps): sequential=${sequentialMs}ms, parallel=${parallelMs}ms")
    }
}
