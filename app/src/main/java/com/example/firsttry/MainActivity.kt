package com.example.firsttry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.firsttry.ui.apps.AppListScreen
import com.example.firsttry.ui.theme.FirstTryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstTryTheme {
                AppListScreen()
            }
        }
    }
}
