package uk.co.freemimp.democreditreport.extension.kotlin

import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.sign

private const val ROUNDING_DECIMAL_PLACES = 4
private const val EPSILON = 0.0001f

fun Float.reduceByEpsilon(
    decimalPlaces: Int = ROUNDING_DECIMAL_PLACES,
    epsilon: Float = EPSILON
): Float {
    return when {
        abs(this) < EPSILON -> 0f
        else -> (this - (epsilon * sign)).round(decimalPlaces)
    }
}

fun Float.round(
    decimalPlaces: Int,
    roundingMode: Int = BigDecimal.ROUND_HALF_DOWN
) = BigDecimal(toDouble())
    .setScale(decimalPlaces, roundingMode)
    .toFloat()

fun Float.toRadians() = Math.toRadians(toDouble()).toFloat()
