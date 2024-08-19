package org.example

import kotlin.math.ln
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class GeometricDistribution(private val p: Double) {
    fun sample(maxSample: Int): Int {
        val sampleP = Random.Default.nextDouble(0.0, 1.0)
        val res = (ln(1 - sampleP) / ln(1 - p)).toInt() - 1
        return max(0, min(res, maxSample))
    }
}