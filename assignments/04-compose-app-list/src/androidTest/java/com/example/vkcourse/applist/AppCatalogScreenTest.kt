package com.example.vkcourse.applist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import org.junit.Rule
import org.junit.Test

class AppCatalogScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun catalogShowsToolbarAndApplicationDetails() {
        composeRule.onNodeWithTag("catalog_toolbar").assertIsDisplayed()
        composeRule.onNodeWithText("Каталог").assertIsDisplayed()
        composeRule.onNodeWithText("ВКонтакте").assertIsDisplayed()
        composeRule.onNodeWithText("Социальные сети").assertIsDisplayed()
    }

    @Test
    fun catalogCanScrollToTheLastApplication() {
        composeRule
            .onNodeWithTag("app_list")
            .performScrollToNode(hasText("VK Образование"))
        composeRule.onNodeWithText("VK Образование").assertIsDisplayed()
    }
}
