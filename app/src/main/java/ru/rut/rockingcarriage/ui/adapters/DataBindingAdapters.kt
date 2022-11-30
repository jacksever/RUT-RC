package ru.rut.rockingcarriage.ui.adapters

import androidx.databinding.BindingAdapter
import ru.rut.rockingcarriage.models.AccelerometerSensor
import ru.rut.rockingcarriage.ui.component.AccelerometerSensorView

@BindingAdapter("android:sensors")
fun setVisibility(view: AccelerometerSensorView, value: AccelerometerSensor?) {
    value?.also {
        view.updateData(it)
    }
}
