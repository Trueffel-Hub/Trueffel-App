package com.example.trueffel_app.ui.screens

import android.Manifest
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.rememberPermissionState
import com.example.trueffel_app.ui.viewmodel.CompassViewModel
import com.example.trueffel_app.ui.viewmodel.Destination
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted


@Composable
fun CompassScreen(viewModel: CompassViewModel) {
    val location by viewModel.location.observeAsState()
    var destination by remember { mutableStateOf(Destination.None) }


        Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Zentriert den Text
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween, // Verteilt Elemente vertikal
            horizontalAlignment = Alignment.CenterHorizontally // Zentriert horizontal
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Abstand oben

            location?.let {
                val distance =viewModel.calculateDistance(viewModel.destination.latitude,viewModel.destination.longitude,it.latitude,it.longitude)
                Text(text = "Entfernung ${viewModel.destination.name} ${String.format("%.4f", distance)} km.")
            } ?: Text(text = "Standort nicht verfügbar")

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        viewModel.getCurrentLocation()
                        viewModel.destination = Destination.Bank    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89B7F5))

                ) {
                    Text("Entfernung Bank")
                }
                Button(
                    onClick = {
                        viewModel.getCurrentLocation()
                        viewModel.destination = Destination.Garten
                              },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89B7F5))
                ) {
                    Text("Entfernung Garten")
                }
            }
        }
    }

}