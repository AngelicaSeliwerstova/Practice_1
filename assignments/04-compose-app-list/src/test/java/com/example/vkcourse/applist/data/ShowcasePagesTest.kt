package com.example.vkcourse.applist.data

import org.junit.Assert.assertEquals
import org.junit.Test

class ShowcasePagesTest {
    @Test
    fun showcaseContainsThreePagesWithThreeApplicationsEach() {
        assertEquals(9, sampleApps.size)
        assertEquals(3, showcasePages.size)
        assertEquals(listOf(3, 3, 3), showcasePages.map { it.size })
    }
}
