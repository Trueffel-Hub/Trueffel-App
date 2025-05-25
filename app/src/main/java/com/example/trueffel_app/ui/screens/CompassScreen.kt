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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted


@Composable
fun CompassScreen(viewModel: CompassViewModel) {
    val location by viewModel.location.observeAsState()

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
                Text(text = "Breitengrad: ${it.latitude}, Längengrad: ${it.longitude}")
            } ?: Text(text = "Standort nicht verfügbar")

            Spacer(modifier = Modifier.weight(1f)) // Abstand zwischen Text und Buttons

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly // Gleichmäßige Verteilung
            ) {
                Button(
                    onClick = { viewModel.getCurrentLocation() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89B7F5))
                ) {
                    Text("Entfernung Bank")
                }
                Button(
                    onClick = { viewModel.getCurrentLocation() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89B7F5))
                ) {
                    Text("Entfernung Garten")
                }
            }
        }
    }
}