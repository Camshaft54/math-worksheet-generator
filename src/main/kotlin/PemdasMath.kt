package org.example

import kotlin.math.exp
import kotlin.random.Random

class PemdasMath {
    companion object {
        fun pemdas(maxLevels: Int = 3): String {
            return (0 until Random.nextInt(2, 4)).map {
                when (Random.nextInt(0, 2)) {
                    0 -> if (maxLevels > 0) "(${pemdas(maxLevels - 1)})" else Random.nextInt(1, 13)
                    1 -> Random.nextInt(1, 13)
                    else -> ""
                }
            }.reduce { acc, expression ->
                if (Random.nextInt(0, 10) < 9) {
                    acc.toString() + randomOperation() + expression
                } else {
                    "\\frac{$acc}{$expression}"
                }
            }.toString()
        }

        private fun randomOperation(): String = when (Random.nextInt(4)) {
            0 -> "+"
            1 -> "-"
            2 -> "\\times"
            3 -> "\\div"
            else -> ""
        }
    }
}