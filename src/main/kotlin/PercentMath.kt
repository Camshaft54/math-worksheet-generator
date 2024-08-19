package org.example

import org.example.Rational.Companion.random

class PercentMath {
    companion object {
        fun percentOf(context: RationalContext) =
            "${random(context).asPercent()}\\text{ of }${random(context).asDecimal()}"

        fun xIsWhatPercentOfY(context: RationalContext): String {
            val x = random(context)
            return "${x.asDecimal()}\\text{ is what percent of }${
                x.multiply(context.geomDenominator.sample(context.denAbsMax) + 1).asDecimal()
            }\\text{?}"
        }
    }
}