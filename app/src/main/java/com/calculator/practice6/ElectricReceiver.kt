package com.calculator.practice6

import com.calculator.practice1.round
import kotlin.math.sqrt

data class ElectricReceiver(
    val name: String, // Найменування ЕП
    val efficiencyCoefficient: Double, // Номінальне значення коефіцієнта корисної дії ЕП (ηн)
    val powerFactor: Double, // Коефіцієнт потужності навантаження (cos φ)
    val loadVoltage: Double, // Напруга навантаження (Uн, кВ)
    val quantity: Double, // Кількість ЕП (n, шт)
    val nominalPower: Double, // Номінальна потужність ЕП (Рн, кВт)
    val totalNominalPower: Double = (quantity * nominalPower).round(4), // n * Рн, кВт
    val utilizationFactor: Double, // Коефіцієнт використання (КВ)
    val reactivePowerFactor: Double, // Коефіцієнт реактивної потужності (tgφ)
    val totalUtilizedPower: Double = (quantity * nominalPower * utilizationFactor).round(4), // n * Рн * КВ
    val totalReactivePower: Double = (totalUtilizedPower * reactivePowerFactor).round(4), // n * Рн * Кв * tgφ, квар
    val totalNominalPowerSquared: Double = (quantity * nominalPower * nominalPower).round(4), // n * Рн^2
    val calculatedGroupCurrent: Double = (totalNominalPower / sqrt(3.0) / loadVoltage /
            powerFactor / efficiencyCoefficient).round(4) // Розрахунковий груповий струм (Ip, А)
)

