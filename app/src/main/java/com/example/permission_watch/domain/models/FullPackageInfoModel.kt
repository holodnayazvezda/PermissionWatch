package com.example.permission_watch.domain.models


data class FullPackageInfoModel(
    val packageName: String,
    val appName: String,
    val versionName: String,
    val versionCode: Long,
    val firstInstallTime: Long,
    val lastUpdateTime: Long,
    val targetSdkVersion: Int,
    val minSdkVersion: Int,
    val permissions: List<String>,
    val isSystemApp: Boolean,
    val hasAccessibility: Boolean,
    val isDeviceAdmin: Boolean,
    val hasUsageStats: Boolean,
    val canDrawOverlays: Boolean
)