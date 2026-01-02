package com.example.permission_watch.presentation.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.permission_watch.presentation.screens.package_details.PackageDetailsScreen
import com.example.permission_watch.presentation.screens.packages.PackagesScreen

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "packages"
    ) {
        composable(NavigationRoutes.Packages.route) {
            PackagesScreen(
                onAppClick = { packageName ->
                    navController.navigate("details/$packageName")
                }
            )
        }
        composable(NavigationRoutes.PackageDetails.route) { backStackEntry ->
            val packageName = backStackEntry.arguments?.getString("packageName")
            val appInfo = remember(packageName) {
                packageName?.let { viewModel.getAppDetails(it) }
            }
            PackageDetailsScreen(
                appInfo = appInfo,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}