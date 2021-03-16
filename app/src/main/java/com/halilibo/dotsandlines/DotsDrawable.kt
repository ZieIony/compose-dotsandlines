package com.halilibo.dotsandlines

import android.graphics.*
import android.graphics.drawable.Drawable
import com.halilibo.dotsandlines.Dot.Companion.distanceTo
import com.halilibo.dotsandlines.DotsAndLinesState.Companion.next
import kotlin.math.sqrt

class DotsDrawable(
    var contentColor: Int = Color.WHITE,
    var threshold: Float,
    var maxThickness: Float,
    var dotRadius: Float,
    var speed: Float,
    var populationFactor: Float
) : Drawable() {
    private var time: Long = 0
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    lateinit var dotsAndLinesState: DotsAndLinesState

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        updateConfig()
    }

    fun updateConfig(){
        dotsAndLinesState = DotsAndLinesState.create(
            IntSize(bounds.width(), bounds.height()),
            populationFactor,
            dotRadius,
            speed
        )
        time = System.currentTimeMillis()
    }

    override fun draw(canvas: Canvas) {
        val currentTime = System.currentTimeMillis()
        dotsAndLinesState = dotsAndLinesState.next(currentTime - time)
        time = currentTime

        paint.style = Paint.Style.FILL_AND_STROKE
        dotsAndLinesState.dots.forEach {
            paint.color = contentColor
            canvas.drawCircle(it.position.x, it.position.y, dotRadius, paint)
        }

        val realThreshold =
            threshold * sqrt((bounds.width() * bounds.width() + bounds.height() * bounds.height()).toDouble())

        dotsAndLinesState.dots.nestedForEach { first, second ->
            val distance = first distanceTo second

            if (distance <= realThreshold) {
                paint.color = contentColor
                paint.strokeWidth =
                    (0.5f + (realThreshold - distance) * maxThickness / realThreshold).toFloat()
                canvas.drawLine(
                    first.position.x,
                    first.position.y,
                    second.position.x,
                    second.position.y,
                    paint
                )
            }
        }

        paint.strokeWidth = maxThickness
        paint.style = Paint.Style.STROKE
        canvas.drawRect(bounds, paint)

        invalidateSelf()
    }

    override fun setAlpha(p0: Int) {
    }

    override fun setColorFilter(p0: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

}
