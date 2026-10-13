package com.example.firsttry.presentation.appdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.firsttry.AppItem
import com.example.firsttry.asSimilarApp
import com.example.firsttry.presentation.applist.AppListItem

@Composable
fun ShowcaseSection(apps: List<AppItem>, onAppClick: () -> Unit) {
    require(apps.size == 9) { "Витрина должна содержать ровно 9 приложений" }
    val pages = apps.chunked(3)
    val pagerState = rememberPagerState(pageCount = { 3 })
    Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Text("Витрина", style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 12.dp))
        HorizontalPager(
            state = pagerState,
            key = { it },
            pageSpacing = 16.dp,
            modifier = Modifier.fillMaxWidth().testTag("showcase-pager"),
        ) { page ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                pages[page].forEach { app ->
                    AppListItem(app = app.asSimilarApp(), onClick = onAppClick,
                        modifier = Modifier.weight(1f).testTag("showcase-app-${app.id}"))
                }
            }
        }
        Text("${pagerState.currentPage + 1} / 3",
            modifier = Modifier.padding(top = 8.dp).testTag("showcase-page"),
            style = MaterialTheme.typography.labelMedium)
    }
}
