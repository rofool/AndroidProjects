package com.example.firsttry.presentation.appdetails

import androidx.compose.runtime.Immutable
import com.example.firsttry.domain.appdetails.AppDetails
import com.example.firsttry.domain.appdetails.SimilarApp

@Immutable
sealed interface AppDetailsState {
    data object Error : AppDetailsState
    data object Loading : AppDetailsState
    data class Content(
        val appDetails: AppDetails,
        val similarApps: List<SimilarApp>,
        val descriptionCollapsed: Boolean,
    ) : AppDetailsState
}
