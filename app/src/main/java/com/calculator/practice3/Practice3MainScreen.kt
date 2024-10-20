package com.calculator.practice3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.calculator.PracticeHeader
import com.calculator.VerticalSpacer


@Composable
fun Practice3MainScreen(navController: NavController) {
    var meanPower by remember { mutableStateOf("") }                  // Average daily power (MW)
    var initialStandardDeviation by remember { mutableStateOf("") }   // Standard deviation before improvement (MW)
    var improvedStandardDeviation by remember { mutableStateOf("") }  // Standard deviation after improvement (MW)
    var energyPrice by remember { mutableStateOf("") }                // Price of electricity (UAH/kWh)
    var netProfit by remember { mutableStateOf(DoubleArray(2) { 0.0 }) }
    var isCalculated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        PracticeHeader("ТВ-13 Руденко О.С. ПР 3", navController)

        TextField(
            value = meanPower,
            onValueChange = { meanPower = it },
            label = { Text("Mean Power (MW)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = initialStandardDeviation,
            onValueChange = { initialStandardDeviation = it },
            label = { Text("Initial Standard Deviation") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = improvedStandardDeviation,
            onValueChange = { improvedStandardDeviation = it },
            label = { Text("Improved Standard Deviation") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = energyPrice,
            onValueChange = { energyPrice = it },
            label = { Text("Energy Price (UAH / kW per hour)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        Button(onClick = {
            netProfit = calculateProfit(
                meanPower.toDoubleOrNull() ?: 0.0,
                initialStandardDeviation.toDoubleOrNull() ?: 0.0,
                improvedStandardDeviation.toDoubleOrNull() ?: 0.0,
                energyPrice.toDoubleOrNull() ?: 0.0
            )
            isCalculated = true
        }) {
            Text("Calculate")
        }
        VerticalSpacer()

        if(isCalculated) {
            Text("Profit before improvement: ${netProfit[0]} thousand UAH")
            Text("Profit after improvement: ${netProfit[1]} thousand UAH")
        }
    }

}
