package com.example.permission_watch.presentation.screens.packages

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.permission_watch.domain.models.PackageInfoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PackagesViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val _packages = MutableStateFlow<List<PackageInfoModel>>(emptyList())
    val packages: StateFlow<List<PackageInfoModel>> = _packages

    init {
        loadPackages()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun loadPackages() {
        viewModelScope.launch(Dispatchers.IO) {
            val context = getApplication<Application>()
            val pm = context.packageManager
            val installedPackages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS)

            val appInfos = installedPackages.map { packageInfo ->
                val appName = packageInfo.applicationInfo?.loadLabel(pm)?.toString() ?: packageInfo.packageName
                val pkgName = packageInfo.packageName
                PackageInfoModel(
                    packageName = pkgName,
                    appName = appName
                )
            }.sortedBy { it.appName.lowercase() }

            _packages.value = appInfos
        }
    }
}
