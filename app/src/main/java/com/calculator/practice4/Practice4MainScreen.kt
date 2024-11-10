package com.calculator.practice4

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
fun Practice4MainScreen(navController: NavController) {
    var shortCircuitCurrent by remember { mutableStateOf("") }
    var fictionTimeOff by remember { mutableStateOf("") }

    var power by remember { mutableStateOf("") }

    var r by remember { mutableStateOf("") }
    var rMin by remember { mutableStateOf("") }
    var x by remember { mutableStateOf("") }
    var xMin by remember { mutableStateOf("") }

    var minConductorSize by remember { mutableDoubleStateOf(0.0) }
    var initialCurrent by remember { mutableDoubleStateOf(0.0) }

    var current by remember { mutableStateOf(listOf(listOf(0.0))) }
    var isCalculated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        PracticeHeader("ТВ-13 Руденко О.С. ПР 4", navController)

        TextField(
            value = shortCircuitCurrent,
            onValueChange = { shortCircuitCurrent = it },
            label = { Text("Short Circuit Current (A)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = fictionTimeOff,
            onValueChange = { fictionTimeOff = it },
            label = { Text("Fiction Time Off (seconds)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = power,
            onValueChange = { power = it },
            label = { Text("Power (MVA)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = r,
            onValueChange = { r = it },
            label = { Text("R (Ohm)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = x,
            onValueChange = { x = it },
            label = { Text("X (Ohm)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = rMin,
            onValueChange = { rMin = it },
            label = { Text("R min (Ohm)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        TextField(
            value = xMin,
            onValueChange = { xMin = it },
            label = { Text("X min (Ohm)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        VerticalSpacer()

        Button(onClick = {
            minConductorSize = getMinConductorSize(
                shortCircuitCurrent.toDoubleOrNull() ?: 0.0,
                fictionTimeOff.toDoubleOrNull() ?: 0.0
            )
            initialCurrent = getInitialCurrent(power.toDoubleOrNull() ?: 0.0)
            current = calculateCurrent(
                listOf(
                    r.toDoubleOrNull() ?: 0.0,
                    rMin.toDoubleOrNull() ?: 0.0,
                    x.toDoubleOrNull() ?: 0.0,
                    xMin.toDoubleOrNull() ?: 0.0
                )
            )

            isCalculated = true
        }) {
            Text("Calculate")
        }
        VerticalSpacer()

        if(isCalculated) {
            Text(
                "Minimal Conductor Size for thermal stability:",
                style = MaterialTheme.typography.titleMedium
            )
            Text("${minConductorSize}mm")
            VerticalSpacer()
            Text(
                "Short-circuit current calculation in 10 (6) kV networks",
                style = MaterialTheme.typography.titleMedium
            )
            Text("Short-circuit currents at the 10 kV bus bars of the substation: ${initialCurrent}kA")
            VerticalSpacer()
            Text(
                "Short-circuit currents for the substation of KhnEM",
                style = MaterialTheme.typography.titleMedium
            )
            PrintList("Short-circuit currents on 10 kV bus bars referred to 110 kV voltage", current[0])
            PrintList("Actual short-circuit currents on 10 kV bus bars", current[1])
            PrintList("Short-circuit currents of outgoing 10 kV lines", current[2])
        }
    }

}

@Composable
fun PrintList(text: String, list: List<Double>) {
    val labels = listOf("I(3)", "I(2)", "I(3)min", "I(2)min")

    VerticalSpacer()
    Text(text, style = MaterialTheme.typography.titleSmall)
    labels.zip(list).forEach { (label, value) -> Text("$label = $value") }
    Text("The emergency mode is not envisaged")
}
