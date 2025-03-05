package com.devname.plinjump.presentation.screen.info

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
fun InfoScreen(navController: NavController) {
    Column(Modifier.fillMaxSize()) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
        Text("Info")
        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(10.dp)) {
            item {
                Text(text = "Lorem ipsum odor amet, consectetuer adipiscing elit. Tristique laoreet mi inceptos sapien ridiculus dui vel dictum diam? Auctor in tempus rhoncus risus ut class nunc odio lacinia. Vulputate molestie gravida justo phasellus malesuada dictum urna elit eleifend. Consectetur praesent bibendum purus vulputate mollis; arcu condimentum faucibus. Ametus commodo ridiculus accumsan nec, consectetur sagittis. Turpis consectetur lacinia luctus magna sem lectus per. Etiam interdum nostra porttitor condimentum convallis arcu. Consectetur suspendisse velit magna cras pulvinar imperdiet integer. Proin sed condimentum etiam libero vehicula consequat hendrerit gravida.")
            }
        }
    }
}