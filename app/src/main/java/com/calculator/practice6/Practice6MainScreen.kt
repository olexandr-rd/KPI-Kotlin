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
//    var linesLength by remember { mutableStateOf("") }
//    var connections by remember { mutableStateOf("") }
//    var emergencyOutagesLosses by remember { mutableStateOf("") }
//    var plannedOutagesLosses by remember { mutableStateOf("") }
//    var failureRates by remember { mutableStateOf(listOf(0.0)) }
//    var moreReliableSystem by remember { mutableStateOf("") }
//    var losses by remember { mutableIntStateOf(0) }
//    var isCalculated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        PracticeHeader("ТВ-13 Руденко О.С. ПР 6", navController)

//        TextField(
//            value = linesLength,
//            onValueChange = { linesLength = it },
//            label = { Text("Length of power transmission lines (km)") },
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Number,
//                imeAction = ImeAction.Next
//            )
//        )
//        VerticalSpacer()
//
//        TextField(
//            value = connections,
//            onValueChange = { connections = it },
//            label = { Text("Number of connections") },
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Number,
//                imeAction = ImeAction.Next
//            )
//        )
//        VerticalSpacer()
//
//        TextField(
//            value = emergencyOutagesLosses,
//            onValueChange = { emergencyOutagesLosses = it },
//            label = { Text("Emergency outages specific losses (UAH/kW·h)") },
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Number,
//                imeAction = ImeAction.Next
//            )
//        )
//        VerticalSpacer()
//
//        TextField(
//            value = plannedOutagesLosses,
//            onValueChange = { plannedOutagesLosses = it },
//            label = { Text("Planned outages specific losses (UAH/kW·h)") },
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Number,
//                imeAction = ImeAction.Next
//            )
//        )
//        VerticalSpacer()
//
//        Button(onClick = {
//            failureRates = calculateFailureRates(
//                linesLength.toDoubleOrNull() ?: 0.0,
//                connections.toDoubleOrNull() ?: 0.0
//            )
//            moreReliableSystem = checkMoreReliableSystem(failureRates)
//            losses = getLosses(
//                emergencyOutagesLosses.toDoubleOrNull() ?: 0.0,
//                plannedOutagesLosses.toDoubleOrNull() ?: 0.0
//            )
//
//            isCalculated = true
//        }) {
//            Text("Calculate")
//        }
//        VerticalSpacer()
//
//        if(isCalculated) {
//            Text(
//                "Systems reliability",
//                style = MaterialTheme.typography.titleMedium
//            )
//            Text("Failure rate of a single-circuit system: ${failureRates[0]} year⁻¹")
//            VerticalSpacer()
//            Text("Failure rate of a double-circuit system (with breaker): ${failureRates[1]} year⁻¹")
//            VerticalSpacer()
//            Text("System that is more reliable: $moreReliableSystem")
//            VerticalSpacer()
//
//            Text(
//                "Losses estimation due to power supply interruptions",
//                style = MaterialTheme.typography.titleMedium
//            )
//            Text("Losses: $losses UAH")
//        }
    }
}
