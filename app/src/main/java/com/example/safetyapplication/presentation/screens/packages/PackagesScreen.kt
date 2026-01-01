package com.example.safetyapplication.presentation.screens.packages

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.safetyapplication.PackagesViewModel

@Composable
fun PackagesScreen(
    viewModel: PackagesViewModel = viewModel()
) {
    val packages by viewModel.packages.collectAsState()

    LazyColumn {
        items(packages) { packageName ->
            Text(text = packageName)
        }
    }
}