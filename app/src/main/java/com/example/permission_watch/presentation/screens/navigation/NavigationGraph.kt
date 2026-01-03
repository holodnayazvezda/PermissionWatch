package com.example.permission_watch.presentation.screens.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                    navController.navigate("package_details/$packageName")
                }
            )
        }
        composable(
            route = NavigationRoutes.PackageDetails.route,
            arguments = listOf(
                navArgument("packageName") { type = NavType.StringType }
            )
        ) {
            PackageDetailsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}