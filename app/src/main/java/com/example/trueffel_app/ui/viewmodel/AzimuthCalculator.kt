package com.example.myapplication

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*

class AzimuthCalculator : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null

    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null

    var azimuth = 0f
    private var updateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = ContextCompat.getSystemService(this, SensorManager::class.java)!!
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        setContent {
            AzimuthScreen(azimuth)
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        magnetometer?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI) }
        startAzimuthUpdates()
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        updateJob?.cancel()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> gravity = event.values
            Sensor.TYPE_MAGNETIC_FIELD -> geomagnetic = event.values
        }
    }

    private fun startAzimuthUpdates() {
        updateJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(250) // Warte 200 Millisekunden (0.2 Sekunden)
                updateAzimuth()
            }
        }
    }

    private fun updateAzimuth() {
        if (gravity != null && geomagnetic != null) {
            val rotationMatrix = FloatArray(9)
            val inclinationMatrix = FloatArray(9)
            if (SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, gravity, geomagnetic)) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientation)
                azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AzimuthScreen(azimuth: Float) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Azimuth Calculator") }) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Azimuth: ${azimuth}°",
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}