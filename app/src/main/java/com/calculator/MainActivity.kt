package com.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "main") {
                composable("main") { MainScreen(navController) }
                composable("calculator1") { Calculator1Screen(navController) }
                composable("calculator2") { Calculator2Screen(navController) }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ТВ-13 Руденко Олександр ПР 1", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("calculator1") }) {
            Text("Calculator 1")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("calculator2") }) {
            Text("Calculator 2")
        }
    }
}


@Composable
fun Calculator1Screen(navController: NavController) {
    val scrollState = rememberScrollState()

    val inputLabels = listOf("Hydrogen (H)", "Carbon (C)", "Sulfur (S)", "Nitrogen (N)", "Oxygen (O)", "Water (W)", "Ash (A)")
    val inputStates = remember { mutableStateListOf("", "", "", "", "", "", "") }

    var dryMass by remember { mutableStateOf<Fuel1?>(null) }
    var dafMass by remember { mutableStateOf<Fuel1?>(null) }
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
        Spacer(modifier = Modifier.height(16.dp))

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
            val fuel = Fuel1(
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

        Spacer(modifier = Modifier.height(16.dp))

        Text("Mass Factor Dry: ${massFactorDry.round()}")
        Text("Mass Factor DAF: ${massFactorDAF.round()}")

        Spacer(modifier = Modifier.height(16.dp))

        dryMass?.let { fuel ->
            FuelCompositionDisplay("Dry Mass:", fuel)
        }
        dafMass?.let { fuel ->
            FuelCompositionDisplay("Dry, Ash Free Mass:", fuel)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Lower Heating Value: ${lowerHeatingValue.round()} MJ/kg")
        Text("LHV Dry: ${lowerHeatingValueDry.round()} MJ/kg")
        Text("LHV Dry Ash-Free: ${lowerHeatingValueDAF.round()} MJ/kg")
    }
}

@Composable
fun FuelCompositionDisplay(title: String, fuel: Fuel1) {
    Text(title)
    Spacer(modifier = Modifier.height(16.dp))
    Text("Hydrogen: ${fuel.h.round()}%")
    Text("Carbon: ${fuel.c.round()}%")
    Text("Sulfur: ${fuel.s.round()}%")
    Text("Nitrogen: ${fuel.n.round()}%")
    Text("Oxygen: ${fuel.o.round()}%")
    Text("Water: ${fuel.w.round()}%")
    Text("Ash: ${fuel.a.round()}%")
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun Calculator2Screen(navController: NavController) {
    val scrollState = rememberScrollState()

    val inputLabels = listOf("Carbon (C, %)", "Hydrogen (H, %)", "Oxygen (O, %)", "Sulfur (S, %)", "Water (W, %)", "Ash (A, %)", "Vanadium (V, mg/kg)", "DAF Value (MJ/kg)")
    val inputStates = remember { mutableStateListOf("", "", "", "", "", "", "", "") }

    var fuelMass by remember { mutableStateOf<Fuel2?>(null) }
    var lhvFromDAF by remember { mutableDoubleStateOf(0.0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.height(16.dp))

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
            val fuel = Fuel2(
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

        Spacer(modifier = Modifier.height(16.dp))

        fuelMass?.let { fuel ->
            Text("Calculated Fuel Mass:")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Carbon: ${fuel.c.round()}%")
            Text("Hydrogen: ${fuel.h.round()}%")
            Text("Oxygen: ${fuel.o.round()}%")
            Text("Sulfur: ${fuel.s.round()}%")
            Text("Water: ${fuel.w.round()}%")
            Text("Ash: ${fuel.a.round()}%")
            Text("Vanadium: ${fuel.v.round()} mg/kg")
            Spacer(modifier = Modifier.height(16.dp))
        }
        Text("LHV from DAF: ${lhvFromDAF.round()} MJ/kg")
    }
}

fun Double.round(): Double {
    return Math.round(this * 100) / 100.0
}

data class Fuel1(
    val h: Double,    // H_P,  %
    val c: Double,    // C_P, %
    val s: Double,    // S_P, %
    val n: Double,    // N_P, %
    val o: Double,    // O_P, %
    val w: Double,    // W_P, %
    val a: Double     // A_P, %
)

data class Fuel2(
    val c: Double,   // C, %
    val h: Double,   // H, %
    val o: Double,   // O, %
    val s: Double,   // S, %
    val w: Double,   // W, %
    val a: Double,   // A, %
    val v: Double    // V, mg/kg
)

fun getMassFactor(wp: Double, ap: Double = 0.0): Double = 100.0 / (100.0 - wp - ap)

fun getMassFactorFromDAFOrDry(wp: Double, ap: Double = 0.0): Double = (100.0 - wp - ap) / 100.0

fun getMass(fuel: Fuel1, includeAsh: Boolean = false): Fuel1 {
    val massFactor = getMassFactor(fuel.w, if (includeAsh) 0.0 else fuel.a)
    return Fuel1(
        fuel.h * massFactor,
        fuel.c * massFactor,
        fuel.s * massFactor,
        fuel.n * massFactor,
        fuel.o * massFactor,
        0.0,
        if (includeAsh) fuel.a * massFactor else 0.0
    )
}

fun getMassFromDAF(fuel: Fuel2): Fuel2 {
    val massFactorFromDAF = getMassFactorFromDAFOrDry(fuel.w, fuel.a)
    val massFactorFromDry = getMassFactorFromDAFOrDry(fuel.w)
    return Fuel2(
        fuel.c * massFactorFromDAF,
        fuel.h * massFactorFromDAF,
        fuel.o * massFactorFromDAF,
        fuel.s * massFactorFromDAF,
        fuel.w,
        fuel.a * massFactorFromDry,
        fuel.v * massFactorFromDry
    )
}

// Base LHV calculation
fun getLowerHeatingValue(fuel: Fuel1): Double = (339 * fuel.c + 1030 * fuel.h - 108.8 * (fuel.o - fuel.s) - 25 * fuel.w) / 1000

// LHV calculation for dry and DAF
fun getAdjustedLowerHeatingValue(fuel: Fuel1, lhv: Double, massFactor: Double): Double = (lhv + 0.025 * fuel.w) * massFactor

// LHV calculation from DAF
fun getLowerHeatingValueFromDAF(fuel: Fuel2, daf: Double): Double = daf * getMassFactorFromDAFOrDry(fuel.w, fuel.a) - 0.025 * fuel.w