package ru.rut.rockingcarriage.ui.adapters

import androidx.databinding.BindingAdapter
import ru.rut.rockingcarriage.models.AccelerometerSensor
import ru.rut.rockingcarriage.ui.components.AccelerometerSensorView

@BindingAdapter("android:sensors")
fun setSensors(view: AccelerometerSensorView, value: AccelerometerSensor?) {
    value?.also {
        view.updateData(it)
    }
}
