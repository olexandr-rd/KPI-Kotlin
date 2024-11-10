package com.calculator.practice4

import com.calculator.practice1.round
import kotlin.math.*

const val thermalCoefficient = 92.0
const val nominalVoltage = 10.5
const val averageVoltage = 10.5
const val nominalPower = 6.3
const val lowVoltage = 11.0
const val highVoltage = 115.0
const val maxFaultVoltage = 11.1
const val reactance = maxFaultVoltage * highVoltage * highVoltage / 100 / nominalPower
const val conversionFactor = lowVoltage * lowVoltage / highVoltage / highVoltage
const val cableLength = 12.37
val cableResistance = listOf(cableLength * 0.64, cableLength * 0.363)
val rootOf3 = sqrt(3.0).round()


// 1
fun getMinConductorSize(shortCircuitCurrent: Double, fictionTimeOff: Double) =
    (shortCircuitCurrent * sqrt(fictionTimeOff) / thermalCoefficient).round()

// 2
fun getResistance(power: Double): Double {
    val x1 = (nominalVoltage.pow(2) / power).round()
    val x2 = (averageVoltage / 100 * nominalVoltage.pow(2) / nominalPower).round()
    return x1 + x2
}

fun getInitialCurrent(power: Double) = (nominalVoltage / rootOf3 / getResistance(power)).round()

// 3
fun getResistanceSum(resistances: List<Double>): List<Double> {
    return resistances.chunked(2) { pair ->
        sqrt(pair[0] * pair[0] + pair[1] * pair[1]).round()
    }
}

fun getCurrent3and2(voltage: Double, resistanceSum: List<Double>): List<Double> {
    return resistanceSum.flatMap { sum ->
        val current3 = (voltage * 1000.0 / rootOf3 / sum).round()
        val current2 = (current3 * rootOf3 / 2).round()
        listOf(current3, current2)
    }
}

fun calculateCurrent(initResistances: List<Double>): List<List<Double>> {
    val resistances = initResistances.mapIndexed { index, item ->
        item + if (index % 2 != 0) reactance else 0.0
    }
    val resistanceSum = getResistanceSum(resistances)
    val current3and2 = getCurrent3and2(highVoltage, resistanceSum)

    val updatedResistances = resistances.map { it * conversionFactor }
    val updatedResistanceSum = getResistanceSum(updatedResistances)
    val updatedCurrent3and2 = getCurrent3and2(lowVoltage, updatedResistanceSum)

    val lastResistances = updatedResistances.mapIndexed { index, item ->
        item + if (index % 2 == 0) cableResistance[0] else cableResistance[1]
    }
    val lastResistanceSum = getResistanceSum(lastResistances)
    val lastCurrent3and2 = getCurrent3and2(lowVoltage, lastResistanceSum)

    return listOf(current3and2, updatedCurrent3and2, lastCurrent3and2)
}
