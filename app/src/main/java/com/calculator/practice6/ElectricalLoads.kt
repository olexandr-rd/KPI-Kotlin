package com.calculator.practice6

import com.calculator.practice1.round
import kotlin.math.*

const val voltage = 0.38

fun calculateElectricalLoad(nominalPower: Double, utilizationFactor: Double, reactivePowerFactor: Double): List<PowerLoad> {
    val electricReceivers = getElectricReceivers(nominalPower, utilizationFactor, reactivePowerFactor)

    val sums = DoubleArray(5) { index ->
        when (index) {
            0 -> electricReceivers.sumOf { it.quantity }
            1 -> electricReceivers.sumOf { it.totalNominalPower }
            2 -> electricReceivers.sumOf { it.totalUtilizedPower }
            3 -> electricReceivers.sumOf { it.totalReactivePower }
            4 -> electricReceivers.sumOf { it.totalNominalPowerSquared }
            else -> 0.0
        }
    }

    val busbar = PowerLoad(
        quantity = sums[0],
        totalNominalPower = sums[1],
        utilizationFactor = getGroupUtilizationFactor(sums[2], sums[1]),
        totalUtilizedPower = sums[2],
        totalReactivePower = sums[3].round(3),
        totalNominalPowerSquared = sums[4],
        effectiveDeviceCount = getEffectiveER(sums[1], sums[4]),
        activePowerCoefficient = 1.25,
        activeLoad = getLoad(1.25, sums[2]),
        reactiveLoad = getLoad(1.0, sums[3].round(3)),
        totalPower = 0.0,
        groupCurrent = 0.0
    )
    busbar.totalPower = getTotalPower(busbar.activeLoad, busbar.reactiveLoad)
    busbar.groupCurrent = getGroupCurrent(busbar.activeLoad)

    val workshop = PowerLoad(
        quantity = 81.0,
        totalNominalPower = 2330.0,
        utilizationFactor = getGroupUtilizationFactor(752.0, 2330.0),
        totalUtilizedPower = 752.0,
        totalReactivePower = 657.0,
        totalNominalPowerSquared = 96399.0,
        effectiveDeviceCount = getEffectiveER(2330.0, 96399.0),
        activePowerCoefficient = 0.7,
        activeLoad = getLoad(0.7, 752.0),
        reactiveLoad = getLoad(0.7, 657.0),
        totalPower = 0.0,
        groupCurrent = 0.0
    )
    workshop.totalPower = getTotalPower(workshop.activeLoad, workshop.reactiveLoad)
    workshop.groupCurrent = getGroupCurrent(workshop.activeLoad)

    return listOf(busbar, workshop)
}

fun getElectricReceivers(nominalPower: Double, utilizationFactor: Double, reactivePowerFactor: Double) = listOf(
    ElectricReceiver(
        name = "Шліфувальний верстат (1-4)",
        efficiencyCoefficient = 0.92,
        powerFactor = 0.9,
        loadVoltage = 0.38,
        quantity = 4.0,
        nominalPower = nominalPower,
        utilizationFactor = 0.15,
        reactivePowerFactor = 1.33,
    ),
    ElectricReceiver(
        name = "Свердлильний верстат (5-6)",
        efficiencyCoefficient = 0.92,
        powerFactor = 0.9,
        loadVoltage = 0.38,
        quantity = 2.0,
        nominalPower = 14.0,
        utilizationFactor = 0.12,
        reactivePowerFactor = 1.0
    ),
    ElectricReceiver(
        name = "Фугувальний верстат (9-12)",
        efficiencyCoefficient = 0.92,
        powerFactor = 0.9,
        loadVoltage = 0.38,
        quantity = 4.0,
        nominalPower = 42.0,
        utilizationFactor = 0.15,
        reactivePowerFactor = 1.33
    ),
    ElectricReceiver(
        name = "Циркулярна пила (13)",
        efficiencyCoefficient = 0.92,
        powerFactor = 0.9,
        loadVoltage = 0.38,
        quantity = 1.0,
        nominalPower = 36.0,
        utilizationFactor = 0.3,
        reactivePowerFactor = reactivePowerFactor
    ),
    ElectricReceiver(
        name = "Прес (16)",
        efficiencyCoefficient = 0.92,
        powerFactor = 0.9,
        loadVoltage = 0.38,
        quantity = 1.0,
        nominalPower = 20.0,
        utilizationFactor = 0.5,
        reactivePowerFactor = 0.75
    ),
    ElectricReceiver(
        name = "Полірувальний верстат (24)",
        efficiencyCoefficient = 0.92,
        powerFactor = 0.9,
        loadVoltage = 0.38,
        quantity = 1.0,
        nominalPower = 40.0,
        utilizationFactor = utilizationFactor,
        reactivePowerFactor = 1.0
    ),
    ElectricReceiver(
        name = "Фрезерний верстат (26-27)",
        efficiencyCoefficient = 0.92,
        powerFactor = 0.9,
        loadVoltage = 0.38,
        quantity = 2.0,
        nominalPower = 32.0,
        utilizationFactor = 0.2,
        reactivePowerFactor = 1.0
    ),
    ElectricReceiver(
        name = "Вентилятор (36)",
        efficiencyCoefficient = 0.92,
        powerFactor = 0.9,
        loadVoltage = 0.38,
        quantity = 1.0,
        nominalPower = 20.0,
        utilizationFactor = 0.65,
        reactivePowerFactor = 0.75
    )
)

fun getGroupUtilizationFactor(totalUtilizedPower: Double, totalNominalPower: Double) =
    (totalUtilizedPower / totalNominalPower).round(4)

fun getEffectiveER(totalNominalPower: Double, totalNominalPowerSquared: Double) =
    ceil(totalNominalPower * totalNominalPower / totalNominalPowerSquared)

fun getLoad(multiplier1: Double, multiplier2: Double) = (multiplier1 * multiplier2).round()

fun getTotalPower(activeLoad: Double, reactiveLoad: Double) =
    sqrt(activeLoad * activeLoad + reactiveLoad * reactiveLoad).round(1)

fun getGroupCurrent(activeLoad: Double) = (activeLoad / voltage).round(2)