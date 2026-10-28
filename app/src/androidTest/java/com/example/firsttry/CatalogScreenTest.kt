package com.example.firsttry

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.lifecycle.ViewModelProvider
import com.example.firsttry.presentation.MainActivity
import com.example.firsttry.presentation.apps.AppListViewModel
import org.junit.Assert.assertSame
import org.junit.Rule
import org.junit.Test

class CatalogScreenTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    @Test fun catalogScrollsToLastApplication() {
        compose.onNodeWithText("Диалог").assertIsDisplayed()
        compose.onNodeWithTag("catalog-list").performScrollToIndex(7)
        compose.onNodeWithText("На волне").assertIsDisplayed()
    }

    @Test fun recreationKeepsViewModelAndDoesNotRepeatSnackbar() {
        lateinit var before: AppListViewModel
        compose.activityRule.scenario.onActivity {
            before = ViewModelProvider(it)[AppListViewModel::class.java]
        }
        compose.onNodeWithTag("catalog-toolbar").performClick()
        compose.onNodeWithText("Здесь собраны приложения на каждый день").assertIsDisplayed()
        compose.activityRule.scenario.recreate()
        compose.activityRule.scenario.onActivity {
            assertSame(before, ViewModelProvider(it)[AppListViewModel::class.java])
        }
        compose.onNodeWithText("Диалог").assertIsDisplayed()
        compose.onNodeWithText("Здесь собраны приложения на каждый день").assertDoesNotExist()
        compose.onNodeWithTag("catalog-toolbar").performClick()
        compose.onNodeWithText("Здесь собраны приложения на каждый день").assertIsDisplayed()
    }
}
