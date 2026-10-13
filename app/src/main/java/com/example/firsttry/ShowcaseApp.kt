package com.example.firsttry

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firsttry.presentation.applist.AppsListScreen
import com.example.firsttry.presentation.appdetails.AppDetailsScreen

@Composable
fun ShowcaseApp() {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        NavHost(
            navController = navController,
            startDestination = "apps",
            modifier = Modifier.fillMaxSize().padding(padding),
        ) {
            composable("apps") {
                AppsListScreen(apps = apps, onAppClick = { app ->
                    navController.navigate("details/${app.id}") { launchSingleTop = true }
                })
            }
            composable(
                route = "details/{appId}",
                arguments = listOf(navArgument("appId") { type = NavType.IntType }),
            ) { entry ->
                val app = apps.firstOrNull { it.id == entry.arguments?.getInt("appId") }
                AppDetailsScreen(app = app, onBackToList = {
                    // Return to the existing list, keeping its scroll position.
                    if (!navController.popBackStack("apps", inclusive = false)) {
                        navController.navigate("apps") { launchSingleTop = true }
                    }
                })
            }
        }
    }
}
