package com.example.firsttry.presentation

import com.example.firsttry.domain.model.AppIcon
import com.example.firsttry.domain.model.CatalogApp
import com.example.firsttry.domain.repository.AppRepository
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

    @Test fun loadsOnceAndNewCollectorsReceiveCachedContent() = runTest(dispatcher) {
        var calls = 0
        val repository = object : AppRepository {
            override suspend fun getApps(): List<CatalogApp> {
                calls++
                return listOf(app)
            }
        }
        val viewModel = AppListViewModel(repository)
        assertEquals(AppListUiState.Loading, viewModel.state.value)
        viewModel.loadApps()
        advanceUntilIdle()
        assertEquals(AppListUiState.Content(listOf(app)), viewModel.state.value)
        repeat(2) {
            val collector = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.state.collect { }
            }
            collector.cancel()
        }
        assertEquals(1, calls)
    }

    @Test fun failedLoadCanBeRetried() = runTest(dispatcher) {
        var fail = true
        val repository = object : AppRepository {
            override suspend fun getApps(): List<CatalogApp> {
                if (fail) error("Unavailable")
                return listOf(app)
            }
        }
        val viewModel = AppListViewModel(repository)
        advanceUntilIdle()
        assertEquals(AppListUiState.Error, viewModel.state.value)
        fail = false
        viewModel.loadApps()
        advanceUntilIdle()
        assertEquals(AppListUiState.Content(listOf(app)), viewModel.state.value)
    }

    @Test fun snackbarEventIsNotReplayedToNewCollector() = runTest(dispatcher) {
        val repository = object : AppRepository {
            override suspend fun getApps() = emptyList<CatalogApp>()
        }
        val viewModel = AppListViewModel(repository)
        val firstEvents = mutableListOf<AppListEvent>()
        val first = backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect { firstEvents.add(it) }
        }
        viewModel.onToolbarClick()
        advanceUntilIdle()
        assertEquals(listOf(AppListEvent.ShowToolbarMessage), firstEvents)
        first.cancel()
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
