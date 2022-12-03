package ru.rut.rockingcarriage.manages

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.core.net.toUri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
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
        val sensorsFile = File(context.filesDir, sensorsFileName)
        val storage = Firebase.storage.reference.child(sensorsFileName)
        if (sensorsFile.exists()) {
            storage.putFile(sensorsFile.toUri())
                .addOnSuccessListener {
                    Timber.w("Pushed file is success!")
                    Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                    if (sensorsFile.exists()) {
                        sensorsFile.delete()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed..", Toast.LENGTH_SHORT).show()
                    Timber.e("Pushed file is failed: ${it.localizedMessage}")
                }
        }
    }

    private fun getFile(): File {
        val file = File(context.filesDir, sensorsFileName)
        if (!file.exists()) {
            Timber.w("Creating a new file")
            file.createNewFile()
        }

        return file
    }

    private fun putHeaders() {
        try {
            csvWriter.writeAll(listOf(arrayOf("DateTime", "X", "Y", "Z")))
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
            } catch (exception: IOException) {
                Timber.e(exception.localizedMessage)
            }
        }
    }

    fun close() {
        try {
            csvWriter.flush()
            csvWriter.close()
        } catch (_: IOException) {
            Timber.i("CSVWriter was already closed")
        }
    }
}