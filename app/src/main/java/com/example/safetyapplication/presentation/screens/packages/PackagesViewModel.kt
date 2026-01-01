package com.example.safetyapplication

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PackagesViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _packages = MutableStateFlow<List<String>>(emptyList())
    val packages: StateFlow<List<String>> = _packages

    init {
        loadPackages()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun loadPackages() {
        viewModelScope.launch(Dispatchers.IO) {
            val pm = getApplication<Application>().packageManager

            val installedPackages = pm
                .getInstalledPackages(0)
                .map { it.packageName }
                .sorted()

            _packages.value = installedPackages
        }
    }
}
