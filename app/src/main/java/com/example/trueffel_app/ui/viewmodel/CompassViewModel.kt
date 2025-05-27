package com.example.trueffel_app.ui.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Math.toDegrees
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import android.hardware.SensorEventListener



enum class Destination(val latitude: Double, val longitude: Double) {
    Bank(52.210111, 10.744470),
    Garten(52.218197, 10.691426),
    //Garten(-52.326913, -100.540318),
    None(0.0, 0.0)
}

class CompassViewModel(application: Application) : AndroidViewModel(application) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private val _location = MutableLiveData<Location?>()


    val location: LiveData<Location?> get() = _location
    var destination = Destination.None

    private val _bearing = MutableStateFlow(0f)
    val bearing = _bearing.asStateFlow()


    private fun calculateBearing(startLat: Double, startLng: Double, endLat: Double, endLng: Double): Float {
        val startLatRad = Math.toRadians(startLat)
        val startLngRad = Math.toRadians(startLng)
        val endLatRad = Math.toRadians(endLat)
        val endLngRad = Math.toRadians(endLng)

        val deltaLng = endLngRad - startLngRad
        val y = sin(deltaLng) * cos(endLatRad)
        val x = cos(startLatRad) * sin(endLatRad) - sin(startLatRad) * cos(endLatRad) * cos(deltaLng)
        return (toDegrees(atan2(y, x)).toFloat() + 360) % 360
    }


    fun updateBearing(currentLat: Double, currentLng: Double, targetLat: Double, targetLng: Double) {
        viewModelScope.launch {
            _bearing.value = calculateBearing(currentLat, currentLng, targetLat, targetLng)

        }
    }




    fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let {
                        _location.value = it
                        updateBearing(it.latitude, it.longitude, destination.latitude, destination.longitude)
                    }
                }
            },
            Looper.getMainLooper()
        )

    }

    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Radius of Earth in kilometers

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a =
            sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(
                2
            )
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c // Distance in kilometers


    }
}


