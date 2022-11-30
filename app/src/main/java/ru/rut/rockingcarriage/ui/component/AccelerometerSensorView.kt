package ru.rut.rockingcarriage.ui.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ru.rut.rockingcarriage.models.AccelerometerSensor

class AccelerometerSensorView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var x1 = 50f
    private var y1 = 50f

    private val radius = 50f

    private var paint = Paint().apply {
        color = Color.parseColor("#163E73")
        isAntiAlias = true
    }

    private var xLike = 0f
    private var yLike = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(x1, y1, radius, paint)
    }

    fun updateData(accelerometerSensor: AccelerometerSensor) {
        setXY(accelerometerSensor.x, accelerometerSensor.y)
    }

    private fun setXY(x: Float, y: Float) {
        xLike += -x
        yLike += y
        x1 += xLike
        y1 += yLike

        val xBorder = measuredWidth
        val yBorder = measuredHeight

        if (x1 > xBorder - 60f) {
            x1 = xBorder - 60f
            xLike = 0f
        } else if (x1 < 60) {
            x1 = 60f
            xLike = 0f
        }

        if (y1 > yBorder - 60f) {
            y1 = yBorder - 60f
            yLike = 0f
        }

        if (y1 < 60) {
            y1 = 60f
            yLike = 0f
        }

        invalidate()
    }
}