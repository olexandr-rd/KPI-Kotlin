package com.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.calculator.practice1.Practice1MainScreen
import com.calculator.practice1.Calculator1Screen
import com.calculator.practice1.Calculator2Screen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "main") {
                // Main selection screen for choosing practices
                composable("main") { MainScreen(navController) }

                // Practice 1 screens
                composable("practice1") { Practice1MainScreen(navController) }
                composable("calculator1") { Calculator1Screen(navController) }
                composable("calculator2") { Calculator2Screen(navController) }

            }
        }
    }
}


@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ТВ-13 Руденко Олександр", style = MaterialTheme.typography.titleLarge)
        VerticalSpacer()
        
        Text(text = "Select Practice", style = MaterialTheme.typography.titleLarge)
        VerticalSpacer()

        Button(onClick = { navController.navigate("practice1") }) {
            Text("Go to Practice 1")
        }
        VerticalSpacer()
    }
}

@Composable
fun VerticalSpacer(height: Int = 16) {
    Spacer(modifier = Modifier.height(height.dp))
}
