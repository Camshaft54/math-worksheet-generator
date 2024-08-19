package org.example

import kotlin.random.Random

class ConvertMath {
    companion object {
        fun convert(source: FDP, destination: FDP, context: RationalContext): String {
            val randomNumber = Rational.random(context).let {
                when (source) {
                    FDP.Fraction -> it.asFraction()
                    FDP.Decimal -> it.asDecimal()
                    FDP.Percent -> it.asPercent()
                }
            }
            return "\\text{Convert }$randomNumber\\text{ to a ${destination.worksheetName}}"
        }
    }
}

enum class FDP(val worksheetName: String) {
    Fraction("fraction"), Decimal("decimal"), Percent("percent");

    companion object {
        fun random(exclude: FDP): FDP = when (Random.Default.nextInt(2)) {
            0 -> if (exclude == Fraction) Decimal else if (exclude == Decimal) Percent else Fraction
            1 -> if (exclude == Fraction) Percent else if (exclude == Percent) Decimal else Fraction
            else -> throw IllegalStateException()
        }
    }
}