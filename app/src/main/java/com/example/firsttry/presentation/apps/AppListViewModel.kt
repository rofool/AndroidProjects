package com.example.firsttry.presentation.apps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppListViewModel(
    private val loadCatalog: suspend () -> List<CatalogApp> = { CatalogData.getApps() },
) : ViewModel() {
    private val mutableState = MutableStateFlow<AppListUiState>(AppListUiState.Loading)
    val state = mutableState.asStateFlow()
    private val mutableEvents = MutableSharedFlow<AppListEvent>(extraBufferCapacity = 1)
    val events = mutableEvents.asSharedFlow()
    private var loadJob: Job? = null

    init {
        loadApps()
    }

    fun loadApps() {
        if (loadJob?.isActive == true) return
        loadJob = viewModelScope.launch {
            mutableState.value = AppListUiState.Loading
            mutableState.value = try {
                AppListUiState.Content(loadCatalog())
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                AppListUiState.Error
            }
        }
    }

    fun onToolbarClick() {
        mutableEvents.tryEmit(AppListEvent.ShowToolbarMessage)
    }

}
