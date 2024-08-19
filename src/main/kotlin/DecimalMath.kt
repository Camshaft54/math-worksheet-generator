package org.example

import org.example.Rational.Companion.random

class DecimalMath {
    companion object {
        fun add(context: RationalContext): String = "${random(context).asDecimal()}+${random(context).asDecimal()}"

        fun sub(context: RationalContext): String = "${random(context).asDecimal()}-${random(context).asDecimal()}"

        fun mul(context: RationalContext): String = "${random(context).asDecimal()}\\times${random(context).asDecimal()}"

        fun div(context: RationalContext): String = "${random(context).asDecimal()}\\div${random(context).asDecimal()}"
    }
}