package com.calculator.practice2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.calculator.PracticeHeader
import com.calculator.VerticalSpacer
import com.calculator.mainCalculations.grossCalculations


@Composable
fun Practice2MainScreen(navController: NavController) {
    var coal by remember { mutableStateOf("") }
    var mazut by remember { mutableStateOf("") }
    var grossCalculated by remember { mutableStateOf(DoubleArray(4) { 0.0 }) }
    var isCalculated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        PracticeHeader("ТВ-13 Руденко О.С. ПР 2", navController)

        TextField(
            value = coal,
            onValueChange = { coal = it },
            label = { Text("Coal mass spent (tonne)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = mazut,
            onValueChange = { mazut = it },
            label = { Text("Mazut mass spent (tonne)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        Button(onClick = {
            grossCalculated = grossCalculations(coal.toDoubleOrNull() ?: 0.0, mazut.toDoubleOrNull() ?: 0.0)
            isCalculated = true
        }) {
            Text("Calculate")
        }
        VerticalSpacer()

        if(isCalculated) {
            Text("1.1. Coal emission factor: ${grossCalculated[0]} g/GJ")
            Text("1.2. Total coal emission: ${grossCalculated[1]} tons")
            Text("1.3. Mazut emission factor: ${grossCalculated[2]}: g/GJ")
            Text("1.4. Total Mazut emission: ${grossCalculated[3]} tons")
            Text("1.5. Gas emission factor: 0 g/GJ")
            Text("1.6. Total coal emission: 0 tons")
        }
    }

}
