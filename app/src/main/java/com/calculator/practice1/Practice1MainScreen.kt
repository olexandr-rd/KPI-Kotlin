package com.calculator.practice1

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.calculator.VerticalSpacer


@Composable
fun Practice1MainScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "ТВ-13 Руденко Олександр ПР 1", style = MaterialTheme.typography.titleLarge)
        VerticalSpacer()
        Button(onClick = { navController.navigate("calculator1") }) {
            Text("Calculator 1")
        }
        VerticalSpacer()
        Button(onClick = { navController.navigate("calculator2") }) {
            Text("Calculator 2")
        }
    }
}
