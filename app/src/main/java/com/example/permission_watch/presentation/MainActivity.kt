package com.example.permission_watch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.permission_watch.presentation.screens.navigation.NavigationGraph
import com.example.permission_watch.presentation.screens.packages.PackagesViewModel
import com.example.permission_watch.presentation.screens.package_details.PackageDetailsScreen
import com.example.permission_watch.presentation.screens.packages.PackagesScreen
import com.example.permission_watch.presentation.theme.PermissionWatchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermissionWatchTheme {
                NavigationGraph()
            }
        }
    }
}
