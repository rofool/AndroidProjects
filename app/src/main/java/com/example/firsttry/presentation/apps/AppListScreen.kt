package com.example.firsttry.presentation.apps

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.firsttry.domain.model.CatalogApp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firsttry.R

@Composable
fun AppListRoute(viewModel: AppListViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current
    val message = stringResource(R.string.catalog_toolbar_message)
    LaunchedEffect(viewModel, lifecycleOwner, message) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.events.collect { event ->
                when (event) {
                    AppListEvent.ShowToolbarMessage -> snackbarHostState.showSnackbar(message)
                }
            }
        }
    }
    AppListScreen(
        state = state,
        onRetry = viewModel::loadApps,
        onToolbarClick = viewModel::onToolbarClick,
        snackbarHostState = snackbarHostState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    state: AppListUiState,
    onRetry: () -> Unit,
    onToolbarClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                modifier = Modifier.testTag("catalog-toolbar").clickable(onClick = onToolbarClick),
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
        Box(Modifier.fillMaxSize().padding(innerPadding)) {
            when (state) {
                AppListUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center).testTag("catalog-loading"),
                )
                AppListUiState.Error -> Column(
                    modifier = Modifier.align(Alignment.Center).padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(stringResource(R.string.catalog_error))
                    Button(onClick = onRetry) { Text(stringResource(R.string.catalog_retry)) }
                }
                is AppListUiState.Content -> {
                    if (state.apps.isEmpty()) {
                        Text(stringResource(R.string.catalog_empty), Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize().testTag("catalog-list"),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(state.apps, key = { it.id }) { app -> AppListItem(app) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AppListItem(app: CatalogApp) {
    Card(
        modifier = Modifier.fillMaxWidth().testTag("app-${app.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Image(
                painter = painterResource(app.icon.drawableRes()),
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
