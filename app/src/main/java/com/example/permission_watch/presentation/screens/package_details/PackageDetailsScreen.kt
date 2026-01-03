package com.example.permission_watch.presentation.screens.package_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageDetailsScreen(
    viewModel: PackageDetailsViewModel = viewModel(factory = PackageDetailsViewModel.Factory),
    onBackClick: () -> Unit
) {
    val fullPackageInfoModel by viewModel.fullPackageInfo.collectAsState()

    if (fullPackageInfoModel != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(fullPackageInfoModel?.appName ?: "Details") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { padding ->
            if (fullPackageInfoModel == null) {
                Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                    Text("App not found", modifier = Modifier.padding(16.dp))
                }
            } else {
                val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item { DetailItem("Package Name", fullPackageInfoModel!!.packageName) }
                    item { DetailItem("Version", fullPackageInfoModel!!.versionName) }
                    item { DetailItem("Version Code", fullPackageInfoModel!!.versionCode.toString()) }
                    item {
                        DetailItem(
                            "System App",
                            if (fullPackageInfoModel!!.isSystemApp) "Yes" else "No"
                        )
                    }
                    item {
                        DetailItem(
                            "Target SDK",
                            fullPackageInfoModel!!.targetSdkVersion.toString()
                        )
                    }
                    item { DetailItem("Min SDK", fullPackageInfoModel!!.minSdkVersion.toString()) }
                    item {
                        DetailItem(
                            "First Install",
                            dateFormat.format(Date(fullPackageInfoModel!!.firstInstallTime))
                        )
                    }
                    item {
                        DetailItem(
                            "Last Update",
                            dateFormat.format(Date(fullPackageInfoModel!!.lastUpdateTime))
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Permissions (${fullPackageInfoModel!!.permissions.size})",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (fullPackageInfoModel!!.permissions.isEmpty()) {
                        item {
                            Text(
                                "No permissions requested",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    } else {
                        items(fullPackageInfoModel!!.permissions.sorted()) { permission ->
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            ) {
                                Text(
                                    text = permission,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    } else {
        Text(text = "package is null")
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
