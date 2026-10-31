package com.example.vkcourse.applist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vkcourse.applist.presentation.CatalogEvent
import com.example.vkcourse.applist.presentation.CatalogViewModel

private const val CATALOG_ROUTE = "catalog"
private const val DETAILS_ROUTE = "details/{appId}"

@Composable
fun AppCatalogApp(
    catalogViewModel: CatalogViewModel = viewModel()
) {
    val navController = rememberNavController()
    val apps by catalogViewModel.apps.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(catalogViewModel, snackbarHostState) {
        catalogViewModel.events.collect { event ->
            when (event) {
                is CatalogEvent.ShowMessage ->
                    snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = CATALOG_ROUTE,
            modifier = Modifier.padding(contentPadding)
        ) {
            composable(CATALOG_ROUTE) {
                AppCatalogScreen(
                    apps = apps,
                    onAppClick = { app ->
                        navController.navigate("details/${app.id}") {
                            launchSingleTop = true
                        }
                    },
                    onToolbarClick = catalogViewModel::onToolbarClick
                )
            }
            composable(
                route = DETAILS_ROUTE,
                arguments = listOf(navArgument("appId") { type = NavType.StringType })
            ) { backStackEntry ->
                val appId = requireNotNull(backStackEntry.arguments?.getString("appId")) {
                    "Маршрут карточки приложения должен содержать appId"
                }
                val app = catalogViewModel.appById(appId)

                if (app == null) {
                    UnknownAppScreen(onBackClick = { navController.popBackStack() })
                } else {
                    AppDetailsScreen(
                        app = app,
                        onBackClick = { navController.popBackStack() },
                        onShareClick = catalogViewModel::onShareClick,
                        onInstallClick = catalogViewModel::onInstallClick,
                        onShowcaseAppClick = {
                            navController.popBackStack(
                                route = CATALOG_ROUTE,
                                inclusive = false
                            )
                        }
                    )
                }
            }
        }
    }
}
