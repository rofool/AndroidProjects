package com.example.firsttry.presentation.apps

sealed interface AppListEvent {
    data object ShowToolbarMessage : AppListEvent
}
