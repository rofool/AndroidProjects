package com.example.firsttry.ui.apps

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firsttry.R
import com.example.firsttry.ui.theme.FirstTryTheme

data class CatalogApp(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    @param:DrawableRes val icon: Int,
)

// Локальные данные: для этого задания сервер и загрузка изображений не нужны.
private val catalogApps = listOf(
    CatalogApp("chat", "Диалог", "Общайтесь с друзьями и делитесь важными моментами.", "Общение", R.drawable.ic_chat),
    CatalogApp("music", "Ритм", "Любимая музыка и плейлисты на каждый день.", "Музыка", R.drawable.ic_music),
    CatalogApp("books", "Книжная полка", "Читайте книги и сохраняйте интересные истории.", "Книги", R.drawable.ic_book),
    CatalogApp("travel", "Маршрут", "Находите новые места и планируйте путешествия.", "Путешествия", R.drawable.ic_travel),
    CatalogApp("notes", "Заметки", "Записывайте идеи и составляйте списки дел.", "Продуктивность", R.drawable.ic_notes),
    CatalogApp("photo", "Фокус", "Сохраняйте лучшие кадры и собирайте фотоальбомы.", "Фотография", R.drawable.ic_photo),
    CatalogApp("learn", "Курс", "Изучайте новое с помощью коротких ежедневных уроков.", "Образование", R.drawable.ic_book),
    CatalogApp("podcast", "На волне", "Слушайте подкасты о науке, культуре и технологиях.", "Развлечения", R.drawable.ic_music),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.catalog_title)) },
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.ic_catalog),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier.padding(start = 16.dp, end = 12.dp).size(36.dp),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(catalogApps, key = { it.id }) { app -> AppListItem(app) }
        }
    }
}

@Composable
private fun AppListItem(app: CatalogApp) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            // Название уже есть рядом: декоративная иконка не дублируется для TalkBack.
            Image(
                painter = painterResource(app.icon),
                contentDescription = null,
                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(14.dp)),
            )
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(app.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    app.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ) {
                    Text(
                        app.category,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "ru", widthDp = 360, heightDp = 800)
@Preview(showBackground = true, locale = "ru", widthDp = 360, heightDp = 800, uiMode = 0x20)
@Composable
private fun AppListPreview() {
    FirstTryTheme(dynamicColor = false) { AppListScreen() }
}
