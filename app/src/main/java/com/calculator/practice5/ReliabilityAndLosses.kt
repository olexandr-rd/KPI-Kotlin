package com.calculator.practice5

import com.calculator.practice1.round
import kotlin.math.*

const val maxDowntimeCoefficient = 43.0
const val plannedDowntimeCoefficient = 1.2 * maxDowntimeCoefficient / 8760.0
const val sectionalBreakerFailure = 0.02

fun calculateFailureRates(linesLength: Double, connections: Double): List<Double> {
    val singleFR = getFailureRate(linesLength, connections)
    val recoverTime = getAverageRecoverTime(linesLength, connections, singleFR)
    val emergencyDowntimeCoefficient = getEmergencyDowntimeCoefficient(singleFR, recoverTime)
    val doubleFR = getDoubleFailureRate(singleFR, emergencyDowntimeCoefficient)
    val doubleFRWithBreaker = getDoubleFailureRateWithBreaker(doubleFR)

    return listOf(singleFR, doubleFRWithBreaker)
}

fun checkMoreReliableSystem(systems: List<Double>): String {
    return (if (systems[0] > systems[1]) "Double" else "Single") + "-circuit system"
}

fun getFailureRate(linesLength: Double, connections: Double) =
    0.01 + 0.007 * linesLength + 0.015 + 0.02 + 0.03 * connections

fun getAverageRecoverTime(linesLength: Double, connections: Double, failureRate: Double) =
    (0.01 * 30 + 0.007 * linesLength * 10 + 0.015 * 100 + 0.02 * 15 + 0.03 * connections * 2) /
            failureRate

fun getEmergencyDowntimeCoefficient(failureRate: Double, recoverTime: Double) =
    failureRate * recoverTime / 8760

fun getDoubleFailureRate(failureRate: Double, emergencyDowntimeCoefficient: Double) =
    2.0 * failureRate * (emergencyDowntimeCoefficient + plannedDowntimeCoefficient)

fun getDoubleFailureRateWithBreaker(doubleFR: Double) = (doubleFR + sectionalBreakerFailure).round(4)

fun getLosses(emergencyOutagesLosses: Double, plannedOutagesLosses: Double): Int {
    val productPmTm = 5.12 * 10.0.pow(3) * 6451
    val emergencyDowntimeME = 0.01 * 45 * 10.0.pow(-3) * productPmTm
    val plannedDowntimeME = 4 * 10.0.pow(-3) * productPmTm

    return (emergencyOutagesLosses * emergencyDowntimeME + plannedOutagesLosses * plannedDowntimeME).roundToInt()
}