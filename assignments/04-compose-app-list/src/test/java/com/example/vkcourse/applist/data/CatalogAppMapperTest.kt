package com.example.vkcourse.applist.data

import com.example.vkcourse.applist.domain.model.CatalogApp
import com.example.vkcourse.applist.domain.model.CatalogAppIcon
import org.junit.Assert.assertEquals
import org.junit.Test

class CatalogAppMapperTest {
    private val dto = CatalogAppDto("music", "Музыка", "Любимые треки", "Развлечения", CatalogAppIcon.MUSIC)
    private val domain = CatalogApp("cloud", "Облако", "Мои файлы", "Работа", CatalogAppIcon.CLOUD)

    @Test
    fun dtoToDomainCopiesEveryField() {
        assertEquals(
            CatalogApp("music", "Музыка", "Любимые треки", "Развлечения", CatalogAppIcon.MUSIC),
            dto.toDomain()
        )
    }

    @Test
    fun domainToDtoCopiesEveryField() {
        assertEquals(
            CatalogAppDto("cloud", "Облако", "Мои файлы", "Работа", CatalogAppIcon.CLOUD),
            domain.toDto()
        )
    }

    @Test
    fun dtoRoundTripPreservesValues() {
        assertEquals(dto, dto.toDomain().toDto())
    }

    @Test
    fun domainRoundTripPreservesValues() {
        assertEquals(domain, domain.toDto().toDomain())
    }

    @Test
    fun mappersPreserveUnicodeAndEmptyText() {
        val unusual = CatalogAppDto("id-1", "Книги 📚", "", "Обучение", CatalogAppIcon.EDUCATION)

        assertEquals(unusual, unusual.toDomain().toDto())
        assertEquals("", unusual.toDomain().description)
    }

    @Test
    fun mappersPreserveEveryIconValue() {
        CatalogAppIcon.entries.forEach { icon ->
            assertEquals(icon, dto.copy(icon = icon).toDomain().icon)
            assertEquals(icon, domain.copy(icon = icon).toDto().icon)
        }
    }
}
