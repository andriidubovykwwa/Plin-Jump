package com.devname.plinjump

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.devname.plinjump.di.appModule
import com.devname.plinjump.presentation.navigation.Navigation
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(application = { modules(appModule) }) {
        MaterialTheme {
            Navigation()
        }
    }
}