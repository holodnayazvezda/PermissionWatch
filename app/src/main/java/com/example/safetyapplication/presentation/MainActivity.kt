package com.example.safetyapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.safetyapplication.presentation.screens.packages.PackagesScreen
import com.example.safetyapplication.presentation.theme.SafetyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafetyApplicationTheme {
                PackagesScreen()
            }
        }
    }
}