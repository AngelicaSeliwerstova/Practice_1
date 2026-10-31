package com.example.vkcourse.applist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import org.junit.Rule
import org.junit.Test

class AppCatalogScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun catalogShowsToolbarAndApplicationDetails() {
        composeRule.onNodeWithTag("catalog_toolbar").assertIsDisplayed()
        composeRule.onNodeWithText("Каталог").assertIsDisplayed()
        composeRule.onNodeWithText("ВКонтакте", substring = true).assertIsDisplayed()
        composeRule.onNodeWithText("Социальные сети").assertIsDisplayed()
    }

    @Test
    fun catalogCanScrollToTheLastApplication() {
        composeRule
            .onNodeWithTag("app_list")
            .performScrollToNode(hasText("VK Звонки"))
        composeRule.onNodeWithText("VK Звонки", substring = true).assertIsDisplayed()
    }

    @Test
    fun appCardOpensDetailsAndShowcaseReturnsToCatalog() {
        composeRule.onNodeWithTag("app_item_vk").performClick()
        composeRule.onNodeWithText("Установить").assertIsDisplayed()
        composeRule.onNodeWithText("Описание").assertIsDisplayed()

        composeRule
            .onNodeWithTag("details_content")
            .performScrollToNode(hasText("Витрина"))
        composeRule.onNodeWithTag("showcase_pager").assertIsDisplayed()
        composeRule.onNodeWithText("1 / 3").assertIsDisplayed()

        composeRule.onNodeWithTag("app_item_music").performClick()
        composeRule.onNodeWithText("Каталог").assertIsDisplayed()
        composeRule.onNodeWithText("Приложения").assertIsDisplayed()
    }

    @Test
    fun showcaseHasThreePagesOfThreeApps() {
        composeRule.onNodeWithTag("app_item_vk").performClick()
        composeRule
            .onNodeWithTag("details_content")
            .performScrollToNode(hasText("Витрина"))

        composeRule.onNodeWithText("ВКонтакте", substring = true).assertIsDisplayed()
        composeRule.onNodeWithText("VK Музыка", substring = true).assertIsDisplayed()
        composeRule.onNodeWithText("VK Видео", substring = true).assertIsDisplayed()

        composeRule.onNodeWithTag("showcase_pager").performTouchInput { swipeLeft() }
        composeRule.onNodeWithText("2 / 3").assertIsDisplayed()
        composeRule.onNodeWithTag("showcase_pager").performTouchInput { swipeLeft() }
        composeRule.onNodeWithText("3 / 3").assertIsDisplayed()
        composeRule.onNodeWithText("VK Звонки", substring = true).assertIsDisplayed()
    }

    @Test
    fun toolbarSnackbarIsNotReplayedAfterActivityRecreation() {
        composeRule.onNodeWithTag("catalog_toolbar").performClick()
        composeRule.onNodeWithText("Каталог приложений готов").assertIsDisplayed()

        composeRule.activityRule.scenario.recreate()

        composeRule.onNodeWithTag("catalog_toolbar").assertIsDisplayed()
        composeRule.onNodeWithText("9 приложений в подборке").assertIsDisplayed()
        composeRule.onAllNodesWithText("Каталог приложений готов").assertCountEquals(0)
    }

    @Test
    fun detailsAndSelectedShowcasePageSurviveActivityRecreation() {
        composeRule.onNodeWithTag("app_item_vk").performClick()
        composeRule
            .onNodeWithTag("details_content")
            .performScrollToNode(hasText("Витрина"))
        composeRule.onNodeWithTag("showcase_pager").performTouchInput { swipeLeft() }
        composeRule.onNodeWithText("2 / 3").assertIsDisplayed()

        composeRule.activityRule.scenario.recreate()

        composeRule.onNodeWithTag("showcase_pager").assertIsDisplayed()
        composeRule.onNodeWithText("2 / 3").assertIsDisplayed()
    }
}
