package com.example.permission_watch.presentation.screens.package_details

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.AppOpsManager
import android.app.Application
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.view.accessibility.AccessibilityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.permission_watch.domain.models.FullPackageInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PackageDetailsViewModel(
    val packageName: String,
    application: Application,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {
    private val _fullPackageInfo = MutableStateFlow<FullPackageInfoModel?>(null)
    val fullPackageInfo: StateFlow<FullPackageInfoModel?> = _fullPackageInfo

    init {
        collectPackageData()
    }

    private fun collectPackageData() {
        if (packageName.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            val context = getApplication<Application>()
            val pm = context.packageManager
            // Get Accessibility services
            val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
            val enabledAccessibilityServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
                ?.map { it.resolveInfo.serviceInfo.packageName } ?: emptyList()
            // Get Device Admins
            val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
            val activeAdmins = dpm.activeAdmins?.map { it.packageName } ?: emptyList()
            // for installed packages
            val installedPackages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)

            val packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            // collect package info
            val appName = packageInfo.applicationInfo?.loadLabel(pm)?.toString() ?: packageInfo.packageName
            val permissions = packageInfo.requestedPermissions?.toList() ?: emptyList()
            val appInfo = packageInfo.applicationInfo
            val pkgName = packageInfo.packageName

            _fullPackageInfo.value = FullPackageInfoModel(
                packageName = pkgName,
                appName = appName,
                versionName = packageInfo.versionName ?: "Unknown",
                versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageInfo.longVersionCode
                } else {
                    @Suppress("DEPRECATION")
                    packageInfo.versionCode.toLong()
                },
                firstInstallTime = packageInfo.firstInstallTime,
                lastUpdateTime = packageInfo.lastUpdateTime,
                targetSdkVersion = appInfo?.targetSdkVersion ?: 0,
                minSdkVersion = appInfo?.minSdkVersion ?: 0,
                permissions = permissions,
                isSystemApp = appInfo?.let { (it.flags and ApplicationInfo.FLAG_SYSTEM) != 0 } ?: false,
                hasAccessibility = enabledAccessibilityServices.contains(pkgName),
                isDeviceAdmin = activeAdmins.contains(pkgName),
                hasUsageStats = hasUsageStatsPermission(context, pkgName),
                canDrawOverlays = Settings.canDrawOverlays(context) && isPackageOverlayEnabled(context, pkgName)
            )
        }
    }

    private fun hasUsageStatsPermission(context: Context, packageName: String): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), packageName)
        } else {
            @Suppress("DEPRECATION")
            appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), packageName)
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }


    private fun isPackageOverlayEnabled(context: Context, packageName: String): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW, Process.myUid(), packageName)
        } else {
            @Suppress("DEPRECATION")
            appOps.checkOpNoThrow(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW, Process.myUid(), packageName)
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }




    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application
                val savedStateHandle = createSavedStateHandle()
                val packageName: String = savedStateHandle["packageName"] ?: ""

                PackageDetailsViewModel(
                    packageName = packageName,
                    application = application,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}