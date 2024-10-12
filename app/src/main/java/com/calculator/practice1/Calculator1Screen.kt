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
import com.calculator.lowerHeatingValue.WorkingFuel
import com.calculator.lowerHeatingValue.getMass
import com.calculator.lowerHeatingValue.getMassFactor
import com.calculator.lowerHeatingValue.getLowerHeatingValue
import com.calculator.lowerHeatingValue.getAdjustedLowerHeatingValue
import com.calculator.lowerHeatingValue.round


@Composable
fun Calculator1Screen(navController: NavController) {
    val scrollState = rememberScrollState()

    val inputLabels = listOf("Hydrogen (H)", "Carbon (C)", "Sulfur (S)", "Nitrogen (N)", "Oxygen (O)", "Water (W)", "Ash (A)")
    val inputStates = remember { mutableStateListOf("", "", "", "", "", "", "") }

    var dryMass by remember { mutableStateOf<WorkingFuel?>(null) }
    var dafMass by remember { mutableStateOf<WorkingFuel?>(null) }
    var massFactorDry by remember { mutableDoubleStateOf(0.0) }
    var massFactorDAF by remember { mutableDoubleStateOf(0.0) }
    var lowerHeatingValue by remember { mutableDoubleStateOf(0.0) }
    var lowerHeatingValueDry by remember { mutableDoubleStateOf(0.0) }
    var lowerHeatingValueDAF by remember { mutableDoubleStateOf(0.0) }

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
            val fuel = WorkingFuel(
                h = inputStates[0].toDoubleOrNull() ?: 0.0,
                c = inputStates[1].toDoubleOrNull() ?: 0.0,
                s = inputStates[2].toDoubleOrNull() ?: 0.0,
                n = inputStates[3].toDoubleOrNull() ?: 0.0,
                o = inputStates[4].toDoubleOrNull() ?: 0.0,
                w = inputStates[5].toDoubleOrNull() ?: 0.0,
                a = inputStates[6].toDoubleOrNull() ?: 0.0
            )

            dryMass = getMass(fuel, true)
            dafMass = getMass(fuel)

            massFactorDry = getMassFactor(fuel.w)
            massFactorDAF = getMassFactor(fuel.w, fuel.a)

            lowerHeatingValue = getLowerHeatingValue(fuel)
            lowerHeatingValueDry = getAdjustedLowerHeatingValue(fuel, lowerHeatingValue, massFactorDry)
            lowerHeatingValueDAF = getAdjustedLowerHeatingValue(fuel, lowerHeatingValue, massFactorDAF)
        }) {
            Text("Calculate")
        }

        VerticalSpacer()

        Text("Mass Factor Dry: ${massFactorDry.round()}")
        Text("Mass Factor DAF: ${massFactorDAF.round()}")

        VerticalSpacer()

        dryMass?.let { fuel ->
            FuelCompositionDisplay("Dry Mass:", fuel)
        }
        dafMass?.let { fuel ->
            FuelCompositionDisplay("Dry, Ash Free Mass:", fuel)
        }

        VerticalSpacer()

        Text("Lower Heating Value: ${lowerHeatingValue.round()} MJ/kg")
        Text("LHV Dry: ${lowerHeatingValueDry.round()} MJ/kg")
        Text("LHV Dry Ash-Free: ${lowerHeatingValueDAF.round()} MJ/kg")
    }
}


@Composable
fun FuelCompositionDisplay(title: String, fuel: WorkingFuel) {
    Text(title)
    VerticalSpacer()
    Text("Hydrogen: ${fuel.h.round()}%")
    Text("Carbon: ${fuel.c.round()}%")
    Text("Sulfur: ${fuel.s.round()}%")
    Text("Nitrogen: ${fuel.n.round()}%")
    Text("Oxygen: ${fuel.o.round()}%")
    Text("Water: ${fuel.w.round()}%")
    Text("Ash: ${fuel.a.round()}%")
    VerticalSpacer()
}