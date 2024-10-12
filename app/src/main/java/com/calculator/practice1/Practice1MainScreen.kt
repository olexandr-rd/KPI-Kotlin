package com.calculator.practice1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.calculator.PracticeHeader
import com.calculator.VerticalSpacer


@Composable
fun Practice1MainScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PracticeHeader("ТВ-13 Руденко О.С. ПР 1", navController)

        Button(onClick = { navController.navigate("calculator1") }) {
            Text("Calculator 1")
        }
        VerticalSpacer()
        Button(onClick = { navController.navigate("calculator2") }) {
            Text("Calculator 2")
        }
    }
}
