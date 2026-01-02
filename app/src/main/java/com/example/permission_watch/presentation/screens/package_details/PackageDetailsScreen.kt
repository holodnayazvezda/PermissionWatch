package com.example.permission_watch.presentation.screens.package_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.permission_watch.domain.models.PackageInfoModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageDetailsScreen(
    packageInfoModel: PackageInfoModel?,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(packageInfoModel?.appName ?: "Details") },
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
        if (packageInfoModel == null) {
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
                item { DetailItem("Package Name", packageInfoModel.packageName) }
                item { DetailItem("Version", packageInfoModel.versionName) }
                item { DetailItem("Version Code", packageInfoModel.versionCode.toString()) }
                item { DetailItem("System App", if (packageInfoModel.isSystemApp) "Yes" else "No") }
                item { DetailItem("Target SDK", packageInfoModel.targetSdkVersion.toString()) }
                item { DetailItem("Min SDK", packageInfoModel.minSdkVersion.toString()) }
                item { DetailItem("First Install", dateFormat.format(Date(packageInfoModel.firstInstallTime))) }
                item { DetailItem("Last Update", dateFormat.format(Date(packageInfoModel.lastUpdateTime))) }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Permissions (${packageInfoModel.permissions.size})",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (packageInfoModel.permissions.isEmpty()) {
                    item { Text("No permissions requested", style = MaterialTheme.typography.bodyMedium) }
                } else {
                    items(packageInfoModel.permissions.sorted()) { permission ->
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
