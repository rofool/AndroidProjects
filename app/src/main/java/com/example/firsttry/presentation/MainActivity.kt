package com.example.firsttry.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firsttry.presentation.apps.AppListRoute
import com.example.firsttry.presentation.apps.AppListViewModel
import com.example.firsttry.presentation.theme.FirstTryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstTryTheme {
                val viewModel: AppListViewModel = viewModel()
                AppListRoute(viewModel)
            }
        }
    }
}
