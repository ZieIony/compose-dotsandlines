package com.halilibo.dotsandlines

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.halilibo.dotsandlines.databinding.MainBinding

class MainActivity : AppCompatActivity() {
    val config = DotsAndLinesConfig()

    val drawables = Array(4) {
        DotsDrawable(
            threshold = config.threshold,
            maxThickness = config.maxThickness,
            dotRadius = config.dotRadius,
            speed = config.speedCoefficient,
            populationFactor = config.population
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.box1.background = drawables[0]
        binding.box2.background = drawables[1]
        binding.box3.background = drawables[2]
        binding.box4.background = drawables[3]
        binding.connectivity.addOnChangeListener { slider, value, fromUser ->
            config.threshold = value
            updateDrawables()
        }
        binding.density.addOnChangeListener { slider, value, fromUser ->
            config.population = value
            updateDrawables()
        }
        binding.dotSize.addOnChangeListener { slider, value, fromUser ->
            config.dotRadius = value
            updateDrawables()
        }
        binding.lineThickness.addOnChangeListener { slider, value, fromUser ->
            config.maxThickness = value
            updateDrawables()
        }
        binding.speed.addOnChangeListener { slider, value, fromUser ->
            config.speedCoefficient = value
            updateDrawables()
        }
    }

    fun updateDrawables() {
        for (d in drawables) {
            d.threshold = config.threshold
            d.maxThickness = config.maxThickness
            d.dotRadius = config.dotRadius
            d.speed = config.speedCoefficient
            d.populationFactor = config.population
            d.updateConfig()
        }
    }
}

data class DotsAndLinesConfig(
    var threshold: Float = 0.1f,
    var maxThickness: Float = 6f,
    var dotRadius: Float = 4f,
    var speedCoefficient: Float = 1f,
    var population: Float = 1f // per 100^2 pixels
)