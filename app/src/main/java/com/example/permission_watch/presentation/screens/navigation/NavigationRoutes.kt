package com.example.permission_watch.presentation.screens.navigation

enum class NavigationRoutes(
    val route: String
) {
    Packages("packages"),
    PackageDetails("package_details/{packageName}")
}
