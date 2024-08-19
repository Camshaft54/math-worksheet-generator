package org.example

import org.example.Rational.Companion.random

class FractionMath {

    companion object {
        fun add(context: RationalContext): String = "${random(context).asFraction()}+${random(context).asFraction()}"

        fun sub(context: RationalContext): String = "${random(context).asFraction()}-${random(context).asFraction()}"

        fun mul(context: RationalContext): String = "${random(context).asFraction()}\\times${random(context).asFraction()}"

        fun div(context: RationalContext): String = "${random(context).asFraction()}\\div${random(context).asFraction()}"

        fun simplify(context: RationalContext): String = "\\text{Simplify }${random(context, hasGCF = true).asFraction()}"
    }
}