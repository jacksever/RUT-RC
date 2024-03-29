package ru.rut.rockingcarriage.manages

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

class AccelerometerManager(context: Context) {
    private var _sensorManager: SensorManager
    private var _accelerometer: Sensor? = null

    val sensorManager: SensorManager get() = _sensorManager
    val accelerometer: Sensor? get() = _accelerometer

    init {
        _sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        _accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
}