package ru.rut.rockingcarriage.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SensorFileViewModel(application: Application) : AndroidViewModel(application) {
    private val _data: MutableLiveData<Long> = MutableLiveData(0L)
    val data: LiveData<Long> = _data

    init {
        viewModelScope.launch {
            while (true) {
                val files = application.filesDir.listFiles { _, name -> name.endsWith(".csv") }
                if (files != null && files.isNotEmpty()) {
                    _data.postValue(files[0].length())
                } else {
                    _data.postValue(0)
                }

                delay(500L)
            }
        }
    }
}