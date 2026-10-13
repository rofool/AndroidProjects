package com.example.firsttry.presentation.appdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.firsttry.AppItem
import com.example.firsttry.apps
import com.example.firsttry.asAppDetails
import kotlinx.coroutines.launch

// Teacher's content UI, with local state instead of Hilt/network/Room.
@Composable
fun AppDetailsScreen(app: AppItem?, onBackToList: () -> Unit) {
    if (app == null) {
        Column {
            Text("Приложение не найдено")
            Button(onClick = onBackToList) { Text("К списку приложений") }
        }
        return
    }
    var descriptionExpanded by rememberSaveable(app.id) { mutableStateOf(false) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val underDevelopment: () -> Unit = {
        scope.launch { snackbar.showSnackbar("В процессе разработки") }
        Unit
    }
    Box(Modifier.fillMaxSize().testTag("app-details")) {
        AppDetailsContent(
            content = AppDetailsState.Content(
                appDetails = app.asAppDetails(), similarApps = emptyList(),
                descriptionCollapsed = descriptionExpanded,
            ),
            onBackClick = onBackToList,
            onShareClick = underDevelopment,
            onInstallClick = underDevelopment,
            onReadMoreClick = { descriptionExpanded = true },
            onDeveloperClick = underDevelopment,
            onSimilarAppDownloadClick = { underDevelopment() },
            modifier = Modifier.fillMaxSize(),
            showcase = { ShowcaseSection(apps, onAppClick = onBackToList) },
        )
        SnackbarHost(snackbar, Modifier.align(Alignment.BottomCenter))
    }
}
