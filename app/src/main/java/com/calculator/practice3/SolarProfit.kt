package com.calculator.practice3

import com.calculator.practice1.round
import kotlin.math.*

fun normalDistribution(p: Double, meanPower: Double, standardDeviation: Double): Double {
    return (1 / (standardDeviation * sqrt(2 * PI))) * exp(-((p - meanPower).pow(2)) / (2 * standardDeviation.pow(2)))
}

fun calculateProfit(meanPower: Double, initialStandardDeviation: Double, improvedStandardDeviation: Double, energyPrice: Double): DoubleArray {
    val (_, initialProfit, initialPenalty) = calculateEnergyWithoutImbalance(meanPower, initialStandardDeviation, energyPrice)
    val netLossBeforeImprovement = (initialProfit - initialPenalty).round()

    val (_, improvedProfit, improvedPenalty) = calculateEnergyWithoutImbalance(meanPower, improvedStandardDeviation, energyPrice)
    val netProfitAfterImprovement = (improvedProfit - improvedPenalty).round()

    return doubleArrayOf(netLossBeforeImprovement, netProfitAfterImprovement)
}

fun calculateEnergyWithoutImbalance(meanPower: Double, standardDeviation: Double, energyPrice: Double): Triple<Double, Double, Double> {
    val imbalanceThreshold = 0.05 // 5% imbalance
    val lowerBound = (meanPower - meanPower * imbalanceThreshold).round()
    val upperBound = (meanPower + meanPower * imbalanceThreshold).round()

    val energyShareWithoutImbalance = integrate({ p -> normalDistribution(p, meanPower, standardDeviation) }, lowerBound, upperBound).round()
    val totalEnergy = (meanPower * 24 * energyShareWithoutImbalance).round()
    val penalty = (meanPower * 24 * (1 - energyShareWithoutImbalance) * energyPrice).round()

    return Triple(energyShareWithoutImbalance, (totalEnergy * energyPrice).round(), penalty)
}

// Trapezoidal integration method
fun integrate(f: (Double) -> Double, a: Double, b: Double, n: Int = 1000): Double {
    val stepSize = (b - a) / n
    var sum = 0.0
    for (i in 0 until n) {
        val x1 = a + i * stepSize
        val x2 = a + (i + 1) * stepSize
        sum += (f(x1) + f(x2)) * stepSize / 2
    }
    return sum
}
