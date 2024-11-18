package com.calculator.practice6


data class PowerLoad(
    val quantity: Double, // сума кількості ЕП (n, шт)
    val totalNominalPower: Double, // сума n * Рн, кВт
    val utilizationFactor: Double, // Коефіцієнт використання (КВ)
    val totalUtilizedPower: Double, // сума n * Рн * КВ
    val totalReactivePower: Double, // сума n * Рн * Кв * tgφ, квар
    val totalNominalPowerSquared: Double, // сума n * Рн^2
    val effectiveDeviceCount: Double, // nв
    val activePowerCoefficient: Double, // Kр
    val activeLoad: Double, // Pp, кВт
    val reactiveLoad: Double, // Qp, квар
    var totalPower: Double, // Sp, кВА
    var groupCurrent: Double // Розрахунковий груповий струм (Ip, А)
)
