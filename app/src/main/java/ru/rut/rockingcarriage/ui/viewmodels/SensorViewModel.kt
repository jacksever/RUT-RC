package ru.rut.rockingcarriage.ui.viewmodels

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.rut.rockingcarriage.manages.AccelerometerManager
import ru.rut.rockingcarriage.manages.CsvFileManager
import ru.rut.rockingcarriage.models.AccelerometerSensor

class SensorViewModel(application: Application) :
    AndroidViewModel(application), SensorEventListener {
    private val _data: MutableLiveData<AccelerometerSensor> = MutableLiveData()
    val data: LiveData<AccelerometerSensor> = _data

    private val accelerometerManager = AccelerometerManager(application)
    private val csvFileManager = CsvFileManager(application)

    override fun onSensorChanged(event: SensorEvent) {
        viewModelScope.launch {
            val sensor = AccelerometerSensor(event.values[0], event.values[1], event.values[2])
            _data.postValue(sensor)
            csvFileManager.putSensorData(sensor)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    fun startRecording() {
        accelerometerManager.accelerometer?.also {
            csvFileManager.startRecording()
            accelerometerManager.sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    fun stopRecording() {
        accelerometerManager.sensorManager.unregisterListener(this)
        csvFileManager.stopRecording()
        _data.postValue(AccelerometerSensor())
        csvFileManager.close()
    }
}