package com.halilibo.dotsandlines

import kotlin.math.sqrt

data class Offset(val x: Float, val y: Float) {
    operator fun plus(offset: Offset): Offset {
        return Offset(x + offset.x, y + offset.y)
    }

    operator fun times(multiplier: Float): Offset {
        return Offset(x * multiplier, y * multiplier)
    }

    operator fun minus(offset: Offset): Offset {
        return Offset(x - offset.x, y - offset.y)
    }

    fun getDistance(): Float {
        return sqrt(x * x + y * y)
    }
}
