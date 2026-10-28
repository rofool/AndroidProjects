package com.example.firsttry.presentation

import com.example.firsttry.presentation.apps.AppIcon
import com.example.firsttry.presentation.apps.CatalogApp
import com.example.firsttry.presentation.apps.AppListEvent
import com.example.firsttry.presentation.apps.AppListUiState
import com.example.firsttry.presentation.apps.AppListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppListViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val app = CatalogApp("id", "Название", "Описание", "Категория", AppIcon.CHAT)

    @Before fun setUp() { Dispatchers.setMain(dispatcher) }
    @After fun tearDown() { Dispatchers.resetMain() }

    @Test fun newCollectorsDoNotReloadContent() = runTest(dispatcher) {
        var calls = 0
        val viewModel = AppListViewModel { calls++; listOf(app) }
        assertEquals(AppListUiState.Loading, viewModel.state.value)
        viewModel.loadApps()
        advanceUntilIdle()
        repeat(2) {
            val collector = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect { }
            }
            collector.cancel()
        }
        assertEquals(1, calls)
        assertEquals(AppListUiState.Content(listOf(app)), viewModel.state.value)
    }

    @Test fun retryLoadsContentAfterFailure() = runTest(dispatcher) {
        var fail = true
        val viewModel = AppListViewModel {
            if (fail) error("Unavailable")
            listOf(app)
        }
        advanceUntilIdle()
        assertEquals(AppListUiState.Error, viewModel.state.value)
        fail = false
        viewModel.loadApps()
        advanceUntilIdle()
        assertEquals(AppListUiState.Content(listOf(app)), viewModel.state.value)
    }

    @Test fun toolbarEventsAreDeliveredOnce() = runTest(dispatcher) {
        val viewModel = AppListViewModel { listOf(app) }
        val firstEvents = mutableListOf<AppListEvent>()
        val collector = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect { firstEvents.add(it) }
        }
        viewModel.onToolbarClick()
        advanceUntilIdle()
        assertEquals(listOf(AppListEvent.ShowToolbarMessage), firstEvents)
        collector.cancel()
        val nextEvents = mutableListOf<AppListEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect { nextEvents.add(it) }
        }
        advanceUntilIdle()
        assertTrue(nextEvents.isEmpty())
        viewModel.onToolbarClick()
        advanceUntilIdle()
        assertEquals(listOf(AppListEvent.ShowToolbarMessage), nextEvents)
    }
}
