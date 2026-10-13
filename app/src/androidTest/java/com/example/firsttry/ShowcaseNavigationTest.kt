package com.example.firsttry

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import org.junit.Rule
import org.junit.Test

class ShowcaseNavigationTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    @Test fun pagerHasThreePagesAndSurvivesRecreation() {
        compose.onNodeWithTag("list-app-1").performClick()
        compose.onNodeWithTag("showcase-pager").performScrollTo()
        compose.onNodeWithTag("showcase-page").assertTextEquals("1 / 3")
        compose.onNodeWithTag("showcase-pager").performTouchInput { swipeLeft() }
        compose.onNodeWithTag("showcase-page").assertTextEquals("2 / 3")
        compose.onNodeWithTag("showcase-pager").performTouchInput { swipeLeft() }
        compose.onNodeWithTag("showcase-page").assertTextEquals("3 / 3")
        compose.onNodeWithTag("showcase-pager").performTouchInput { swipeLeft() }
        compose.onNodeWithTag("showcase-page").assertTextEquals("3 / 3")
        compose.activityRule.scenario.recreate()
        compose.onNodeWithTag("showcase-pager").performScrollTo()
        compose.onNodeWithTag("showcase-page").assertTextEquals("3 / 3")
        compose.onNodeWithTag("showcase-pager").performTouchInput { swipeRight() }
        compose.onNodeWithTag("showcase-page").assertTextEquals("2 / 3")
    }

    @Test fun everyShowcaseItemReturnsToList() {
        for (id in 1..9) {
            compose.onNodeWithTag("list-app-1").performClick()
            compose.onNodeWithTag("app-details").assertIsDisplayed()
            compose.onNodeWithTag("showcase-pager").performScrollTo()
            repeat((id - 1) / 3) {
                compose.onNodeWithTag("showcase-pager").performTouchInput { swipeLeft() }
                compose.waitForIdle()
            }
            compose.onNodeWithTag("showcase-app-$id").performClick()
            compose.onNodeWithTag("apps-list").assertIsDisplayed()
        }
        compose.onNodeWithTag("list-app-2").performClick()
        compose.onNodeWithContentDescription("Назад").performClick()
        compose.onNodeWithTag("apps-list").assertIsDisplayed()
    }
}
