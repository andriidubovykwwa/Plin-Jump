package com.devname.plinjump.presentation.screen.skins

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SkinsScreen(navController: NavController) {
    Column(Modifier.fillMaxSize()) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
        Text("Skins")
        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)) {
            item {
                Text(text = "Skins")
            }
        }
    }
}