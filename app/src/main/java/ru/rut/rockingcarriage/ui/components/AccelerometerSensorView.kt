package ru.rut.rockingcarriage.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ru.rut.rockingcarriage.models.AccelerometerSensor

class AccelerometerSensorView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var x1 = 50F
    private var y1 = 50F

    private var dataX = 0F
    private var dataY = 0F

    private val radius = 50F

    private var paint = Paint().apply {
        color = Color.parseColor("#163E73")
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(x1, y1, radius, paint)
    }

    fun updateData(accelerometerSensor: AccelerometerSensor) {
        setXY(accelerometerSensor.x, accelerometerSensor.y)
    }

    private fun setXY(x: Float, y: Float) {
        dataX += -x
        dataY += y
        x1 += dataX
        y1 += dataY

        val xBorder = measuredWidth
        val yBorder = measuredHeight

        if (x1 > xBorder - 60F) {
            x1 = xBorder - 60F
            dataX = 0F
        } else if (x1 < 60) {
            x1 = 60F
            dataX = 0F
        }

        if (y1 > yBorder - 60F) {
            y1 = yBorder - 60F
            dataY = 0f
        }

        if (y1 < 60) {
            y1 = 60F
            dataY = 0F
        }

        invalidate()
    }
}