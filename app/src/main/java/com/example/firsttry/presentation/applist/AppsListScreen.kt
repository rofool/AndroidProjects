package com.example.firsttry.presentation.applist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.firsttry.AppItem
import com.example.firsttry.asSimilarApp

@Composable
fun AppsListScreen(apps: List<AppItem>, onAppClick: (AppItem) -> Unit) {
    Column(Modifier.fillMaxSize().testTag("apps-list")) {
        Text("Приложения", style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(apps, key = { it.id }) { app ->
                AppListItem(app = app.asSimilarApp(), onClick = { onAppClick(app) },
                    modifier = Modifier.testTag("list-app-${app.id}"))
            }
        }
    }
}
