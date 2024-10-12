package com.calculator.mainCalculations

import kotlin.math.pow

fun grossCalculations(coalSpent: Double, mazutSpent: Double): DoubleArray {
    val coalLHV = 20.47
    val mazutDAF = 40.40
    val mazutFuel = CombustibleFuel(85.5, 11.2, 0.8, 2.5, 2.0, 0.15, 333.3)
    val mazutLHV: Double = getLowerHeatingValueFromDAF(mazutFuel, mazutDAF)

    val coalSolidParticles = SolidParticles(20.47, 0.8, 25.2, 1.5, 0.985, 0.0)
    val mazutSolidParticles = SolidParticles(mazutLHV, 1.0, 0.15, 0.0, 0.985, 0.0)

    val coalEmissions = getEmissions(coalSolidParticles)
    val mazutEmissions = getEmissions(mazutSolidParticles)

    val coalGross = getGross(coalEmissions, coalLHV, coalSpent)
    val mazutGross = getGross(mazutEmissions, mazutLHV, mazutSpent)

    return doubleArrayOf(coalEmissions.round(), coalGross.round(), mazutEmissions.round(), mazutGross.round())
}

fun getEmissions(sp: SolidParticles) = 10.0.pow(6) / sp.lhv * sp.vol * sp.ash /
        (100 - sp.mass) * (1 - sp.ashCapture) + sp.emissions

fun getGross(emissions: Double, lhv: Double, fuelSpent: Double) = 10.0.pow(-6) * emissions * lhv * fuelSpent
