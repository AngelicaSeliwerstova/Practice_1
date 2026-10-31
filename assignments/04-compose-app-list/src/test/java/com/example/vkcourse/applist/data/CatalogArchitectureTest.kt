package com.example.vkcourse.applist.data

import com.example.vkcourse.applist.domain.model.CatalogAppIcon
import com.example.vkcourse.applist.domain.model.SHOWCASE_PAGE_COUNT
import com.example.vkcourse.applist.domain.model.SHOWCASE_PAGE_SIZE
import com.example.vkcourse.applist.domain.usecase.GetCatalogAppsUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class CatalogArchitectureTest {
    @Test
    fun mapperConvertsDtoFieldsToDomainModel() {
        val dto = CatalogDataSource().getApps().first()

        val app = dto.toDomain()

        assertEquals(dto.id, app.id)
        assertEquals(dto.name, app.name)
        assertEquals(dto.description, app.description)
        assertEquals(dto.category, app.category)
        assertEquals(dto.icon, app.icon)
    }

    @Test
    fun useCaseReturnsNineMappedApplicationsThroughRepository() {
        val repository = DefaultCatalogRepository(CatalogDataSource())
        val apps = GetCatalogAppsUseCase(repository)()

        assertEquals(9, apps.size)
        val pages = apps.chunked(SHOWCASE_PAGE_SIZE)
        assertEquals(SHOWCASE_PAGE_COUNT, pages.size)
        assertEquals(listOf(3, 3, 3), pages.map { it.size })
        assertEquals("ВКонтакте", apps.first().name)
        assertEquals(CatalogAppIcon.SOCIAL, apps.first().icon)
        assertNotNull(apps.firstOrNull { it.id == "calls" })
    }
}
