package ru.rut.rockingcarriage.manages

import android.content.Context
import android.os.Build
import com.opencsv.CSVWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import ru.rut.rockingcarriage.models.AccelerometerSensor
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.io.IOException

class CsvFileManager(private val context: Context) {
    private lateinit var sensorsFileName: String
    private lateinit var csvWriter: CSVWriter

    fun startRecording() {
        sensorsFileName = "${Build.MANUFACTURER}_${Build.MODEL}_${Clock.System.now()}.csv"
        csvWriter = CSVWriter(FileWriter(getFile()))

        if (getFile().length() == 0L) putHeaders()
    }

    fun stopRecording() {
        //TODO: transfer file to Firebase

        //val file = File(context.filesDir, sensorsFileName)
        //if (file.exists()) {
        //    file.delete()
        //}
    }

    private fun getFile(): File {
        val file = File(context.filesDir, sensorsFileName)
        if (!file.exists()) {
            Timber.w("Not existing")
            file.createNewFile()
        }

        return file
    }

    private fun putHeaders() {
        try {
            csvWriter.writeAll(listOf(arrayOf("DateTime", "X", "Y", "Z")))
            Timber.w("Write headers to CSV file")
        } catch (exception: IOException) {
            Timber.e(exception.localizedMessage)
        }
    }

    suspend fun putSensorData(sensor: AccelerometerSensor) {
        withContext(Dispatchers.IO) {
            try {
                csvWriter.writeAll(
                    listOf(
                        arrayOf(
                            Clock.System.now().toString(),
                            sensor.x.toString(),
                            sensor.y.toString(),
                            sensor.z.toString()
                        )
                    )
                )
                Timber.i("Write sensor data to CSV file")
            } catch (exception: IOException) {
                Timber.e(exception.localizedMessage)
            }
        }
    }

    fun close() {
        csvWriter.close()
    }
}