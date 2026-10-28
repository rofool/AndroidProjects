package com.example.firsttry.presentation.apps

import com.example.firsttry.presentation.apps.CatalogApp

sealed interface AppListUiState {
    data object Loading : AppListUiState
    data class Content(val apps: List<CatalogApp>) : AppListUiState
    data object Error : AppListUiState
}
