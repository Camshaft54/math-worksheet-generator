package org.example

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class Rational(private val p: Int, private val q: Int, private val decimalPlaces: Int = 2) {
    private val bigDecimal = p.toBigDecimal().setScale(decimalPlaces).divide(q.toBigDecimal(), RoundingMode.HALF_UP)

    fun asFraction(): String = "\\frac{$p}{$q}"

    fun asDecimal(): String = bigDecimal.setScale(decimalPlaces).toString().replace("\\.*0*\$".toRegex(), "")

    fun asPercent(): String = "${bigDecimal.movePointRight(2).toString().replace("\\.*0*\$".toRegex(), "")}\\%"

    fun multiply(scalar: Int): Rational {
        return Rational(p * scalar, q)
    }

    companion object {
        fun random(context: RationalContext, hasGCF: Boolean = false): Rational {
            if (!hasGCF) {
                return random(
                    context.from,
                    context.until,
                    context.decimalPlaces,
                    context.numAbsMax,
                    context.denAbsMax,
                    context.maxIterations,
                    context.geomNumerator,
                    context.geomDenominator
                )
            } else {
                for (i in 0 until context.maxIterations) {
                    val p =
                        BigInteger.valueOf(context.geomNumerator.sample(context.numAbsMax).toLong()) + BigInteger.ONE
                    val q =
                        BigInteger.valueOf(context.geomDenominator.sample(context.denAbsMax).toLong()) + BigInteger.ONE
                    if (p.gcd(q) > BigInteger.ONE) {
                        return Rational(p.toInt(), q.toInt(), decimalPlaces = context.decimalPlaces)
                    }
                }
                throw IllegalArgumentException("Could not generate random rational in ${context.maxIterations} iterations")
            }
        }

        fun random(
            from: BigDecimal,
            until: BigDecimal,
            decimalPlaces: Int,
            numAbsMax: Int,
            denAbsMax: Int,
            maxIterations: Int,
            geomNumerator: GeometricDistribution,
            geomDenominator: GeometricDistribution
        ): Rational {
            var p: Int
            var q: Int
            var iters = 0
            do {
                if (iters > maxIterations) {
                    throw IllegalArgumentException("Could not generate random rational in $maxIterations iterations")
                }
                p = geomNumerator.sample(numAbsMax) + 1
                q = geomDenominator.sample(denAbsMax) + 2
                val value = p.toFloat() / q.toFloat()
                iters++
            } while (value <= from.toFloat() && value >= until.toFloat())
            return Rational(p, q, decimalPlaces = decimalPlaces)
        }
    }
}

data class RationalContext(
    val from: BigDecimal,
    val until: BigDecimal,
    val numAbsMax: Int,
    val denAbsMax: Int,
    val decimalPlaces: Int = 2,
    val maxIterations: Int = 1000,
    val geomNumerator: GeometricDistribution = GeometricDistribution(0.01),
    val geomDenominator: GeometricDistribution = GeometricDistribution(0.05)
)