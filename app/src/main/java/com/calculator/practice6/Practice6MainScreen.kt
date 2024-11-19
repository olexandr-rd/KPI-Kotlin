package com.calculator.practice6

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.calculator.PracticeHeader
import com.calculator.VerticalSpacer


@Composable
fun Practice6MainScreen(navController: NavController) {
    var nominalPower by remember { mutableStateOf("") }
    var utilizationFactor by remember { mutableStateOf("") }
    var reactivePowerFactor by remember { mutableStateOf("") }
    var powerLoads by remember { mutableStateOf<List<PowerLoad>>(emptyList()) }
    var isCalculated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        PracticeHeader("ТВ-13 Руденко О.С. ПР 6", navController)

        TextField(
            value = nominalPower,
            onValueChange = { nominalPower = it },
            label = { Text("Rated Power of the Electric Motor (Grinding Machine) Pₙ, kW") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = utilizationFactor,
            onValueChange = { utilizationFactor = it },
            label = { Text("Utilization Factor (Polishing Machine) Kᵤ") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = reactivePowerFactor,
            onValueChange = { reactivePowerFactor = it },
            label = { Text("Reactive Power Factor (Circular Saw) tgφ") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        Button(onClick = {
            powerLoads = calculateElectricalLoad(
                nominalPower.toDoubleOrNull() ?: 0.0,
                utilizationFactor.toDoubleOrNull() ?: 0.0,
                reactivePowerFactor.toDoubleOrNull() ?: 0.0
            )

            isCalculated = true
        }) {
            Text("Calculate")
        }
        VerticalSpacer()

        if(isCalculated) {
            Text(
                "For the given composition of electric motors (EM) and their characteristics" +
                    " in the workshop network, the power load will be as follows:",
                style = MaterialTheme.typography.titleMedium
            )
            Text("Group utilization factor for SR1=SR2=SR3: ${powerLoads[0].utilizationFactor}")
            Text("Effective number of EMs for SR1=SR2=SR3: ${powerLoads[0].effectiveDeviceCount}")
            Text("Calculated active power factor for SR1=SR2=SR3: ${powerLoads[0].activePowerCoefficient}")
            Text("Calculated active load for SR1=SR2=SR3: ${powerLoads[0].activeLoad} kW")
            Text("Calculated reactive load for SR1=SR2=SR3: ${powerLoads[0].reactiveLoad} kvar")
            Text("Total power for SR1=SR2=SR3: ${powerLoads[0].totalPower} kVA")
            Text("Calculated group current for SR1=SR2=SR3: ${powerLoads[0].groupCurrent} A")
            Text("Utilization factor for the workshop as a whole: ${powerLoads[1].utilizationFactor}")
            Text("Effective number of EMs in the workshop as a whole: ${powerLoads[1].effectiveDeviceCount}")
            Text("Calculated active power factor for the workshop as a whole: ${powerLoads[1].activePowerCoefficient}")
            Text("Calculated active load on the 0.38 kV busbars of the substation: ${powerLoads[1].activeLoad}4 kW")
            Text("Calculated reactive load on the 0.38 kV busbars of the substation: ${powerLoads[1].reactiveLoad} kvar")
            Text("Total power on the 0.38 kV busbars of the substation: ${powerLoads[1].totalPower} kVA")
            Text("Calculated group current on the 0.38 kV busbars of the substation: ${powerLoads[1].groupCurrent} A")
        }
    }
}
