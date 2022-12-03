package ru.rut.rockingcarriage.ui.adapters

import android.text.format.Formatter.formatShortFileSize
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.rut.rockingcarriage.R
import ru.rut.rockingcarriage.models.AccelerometerSensor
import ru.rut.rockingcarriage.ui.components.AccelerometerSensorView

@BindingAdapter("android:sensors")
fun setSensors(view: AccelerometerSensorView, value: AccelerometerSensor?) {
    value?.also {
        view.updateData(it)
    }
}

@BindingAdapter("android:weight")
fun setTextWeightFile(view: TextView, value: Long?) {
    value?.also {
        view.text =
            view.context.getString(R.string.info_file, formatShortFileSize(view.context, it))
    }
}
