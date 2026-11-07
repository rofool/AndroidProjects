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
import com.example.firsttry.presentation.apps.AppListUiState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

class CatalogScreenTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    private fun awaitCatalog(): AppListUiState.Content {
        lateinit var viewModel: AppListViewModel
        compose.activityRule.scenario.onActivity {
            viewModel = ViewModelProvider(it)[AppListViewModel::class.java]
        }
        compose.waitUntil(timeoutMillis = 35000) { viewModel.state.value !is AppListUiState.Loading }
        assertTrue("Server catalog should load", viewModel.state.value is AppListUiState.Content)
        return viewModel.state.value as AppListUiState.Content
    }

    @Test fun catalogLoadsFromServerAndScrollsToLastApplication() {
        val apps = awaitCatalog().apps
        assertTrue(apps.isNotEmpty())
        compose.onNodeWithText(apps.first().name).assertIsDisplayed()
        compose.onNodeWithTag("catalog-list").performScrollToIndex(apps.lastIndex)
        compose.onNodeWithText(apps.last().name).assertIsDisplayed()
    }

    @Test fun detailsCanBeLoadedWithoutOpeningAnotherScreen() = runBlocking {
        val app = awaitCatalog().apps.first()
        val repository = (compose.activity.application as CatalogApplication).container.appRepository
        val details = repository.getAppDetails(app.id)
        assertEquals(app.id, details.id)
        assertTrue(details.description.isNotBlank())
    }

    @Test fun recreationKeepsViewModelAndDoesNotRepeatSnackbar() {
        val firstApp = awaitCatalog().apps.first()
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
        compose.onNodeWithText(firstApp.name).assertIsDisplayed()
        compose.onNodeWithText("Здесь собраны приложения на каждый день").assertDoesNotExist()
        compose.onNodeWithTag("catalog-toolbar").performClick()
        compose.onNodeWithText("Здесь собраны приложения на каждый день").assertIsDisplayed()
    }
}
