package com.calculator.practice1

import kotlin.math.round

fun Double.round(decimals: Int = 2): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun getMassFactor(wp: Double, ap: Double = 0.0): Double = 100.0 / (100.0 - wp - ap)

fun getMassFactorFromDAFOrDry(wp: Double, ap: Double = 0.0): Double = (100.0 - wp - ap) / 100.0

fun getMass(fuel: WorkingFuel, includeAsh: Boolean = false): WorkingFuel {
    val massFactor = getMassFactor(fuel.w, if (includeAsh) 0.0 else fuel.a)
    return WorkingFuel(
        fuel.h * massFactor,
        fuel.c * massFactor,
        fuel.s * massFactor,
        fuel.n * massFactor,
        fuel.o * massFactor,
        0.0,
        if (includeAsh) fuel.a * massFactor else 0.0
    )
}

fun getMassFromDAF(fuel: CombustibleFuel): CombustibleFuel {
    val massFactorFromDAF = getMassFactorFromDAFOrDry(fuel.w, fuel.a)
    val massFactorFromDry = getMassFactorFromDAFOrDry(fuel.w)
    return CombustibleFuel(
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
fun getLowerHeatingValue(fuel: WorkingFuel): Double = (339 * fuel.c + 1030 * fuel.h - 108.8 * (fuel.o - fuel.s) - 25 * fuel.w) / 1000

// LHV calculation for dry and DAF
fun getAdjustedLowerHeatingValue(fuel: WorkingFuel, lhv: Double, massFactor: Double): Double = (lhv + 0.025 * fuel.w) * massFactor

// LHV calculation from DAF
fun getLowerHeatingValueFromDAF(fuel: CombustibleFuel, daf: Double): Double = daf * getMassFactorFromDAFOrDry(fuel.w, fuel.a) - 0.025 * fuel.w
