package com.calculator.practice1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.calculator.VerticalSpacer
import com.calculator.lowerHeatingValue.CombustibleFuel
import com.calculator.lowerHeatingValue.getMassFromDAF
import com.calculator.lowerHeatingValue.getLowerHeatingValueFromDAF
import com.calculator.lowerHeatingValue.round


@Composable
fun Calculator2Screen(navController: NavController) {
    val scrollState = rememberScrollState()

    val inputLabels = listOf("Carbon (C, %)", "Hydrogen (H, %)", "Oxygen (O, %)", "Sulfur (S, %)", "Water (W, %)", "Ash (A, %)", "Vanadium (V, mg/kg)", "DAF Value (MJ/kg)")
    val inputStates = remember { mutableStateListOf("", "", "", "", "", "", "", "") }

    var fuelMass by remember { mutableStateOf<CombustibleFuel?>(null) }
    var lhvFromDAF by remember { mutableDoubleStateOf(0.0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        VerticalSpacer()

        inputLabels.forEachIndexed { index, label ->
            TextField(
                value = inputStates[index],
                onValueChange = { inputStates[index] = it },
                label = { Text(label) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(onClick = {
            val fuel = CombustibleFuel(
                c = inputStates[0].toDoubleOrNull() ?: 0.0,
                h = inputStates[1].toDoubleOrNull() ?: 0.0,
                o = inputStates[2].toDoubleOrNull() ?: 0.0,
                s = inputStates[3].toDoubleOrNull() ?: 0.0,
                w = inputStates[4].toDoubleOrNull() ?: 0.0,
                a = inputStates[5].toDoubleOrNull() ?: 0.0,
                v = inputStates[6].toDoubleOrNull() ?: 0.0
            )

            fuelMass = getMassFromDAF(fuel)
            lhvFromDAF = getLowerHeatingValueFromDAF(fuel, inputStates[7].toDoubleOrNull() ?: 0.0)
        }) {
            Text("Calculate")
        }

        VerticalSpacer()

        fuelMass?.let { fuel ->
            Text("Calculated Fuel Mass:")
            VerticalSpacer()
            Text("Carbon: ${fuel.c.round()}%")
            Text("Hydrogen: ${fuel.h.round()}%")
            Text("Oxygen: ${fuel.o.round()}%")
            Text("Sulfur: ${fuel.s.round()}%")
            Text("Water: ${fuel.w.round()}%")
            Text("Ash: ${fuel.a.round()}%")
            Text("Vanadium: ${fuel.v.round()} mg/kg")
            VerticalSpacer()
        }
        Text("LHV from DAF: ${lhvFromDAF.round()} MJ/kg")
    }
}

