package com.example.firsttry.presentation.apps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.firsttry.domain.repository.AppRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppListViewModel(private val repository: AppRepository) : ViewModel() {
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
                AppListUiState.Content(repository.getApps())
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

    companion object {
        fun factory(repository: AppRepository) = viewModelFactory {
            initializer { AppListViewModel(repository) }
        }
    }
}
