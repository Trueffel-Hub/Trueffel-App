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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.rememberPermissionState
import com.example.trueffel_app.ui.viewmodel.CompassViewModel
import com.example.trueffel_app.ui.viewmodel.Destination
import com.geeksforgeeks.demo.utils.CustomColors
import com.geeksforgeeks.demo.utils.CustomColors.ButtonBlue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted


@Composable
fun CompassScreen(viewModel: CompassViewModel) {
    val location by viewModel.location.observeAsState()
    val fontSize: Int = 30
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.StandardGrey)
    ) {


        Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            location?.let {
                val distance =viewModel.calculateDistance(viewModel.destination.latitude,viewModel.destination.longitude,it.latitude,it.longitude)
                Text(
                    text = "Luftlinie zum/zur ${viewModel.destination.name} ${String.format("%.4f", distance)} km.",
                    color = CustomColors.ShadowedWhite,
                    fontSize = fontSize.sp
                )

            } ?:Text(
                text = "Standort nicht verfügbar",
                color = CustomColors.ShadowedWhite,
                fontSize = fontSize.sp
            )

            Spacer(modifier = Modifier.weight(1f))
            CreateButtons(viewModel)

        }
    }

}}


@Composable
fun CreateButtons(viewModel: CompassViewModel){
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                viewModel.getCurrentLocation()
                viewModel.destination = Destination.Bank    },
            colors = ButtonDefaults.buttonColors(containerColor =ButtonBlue),
            modifier = Modifier
                .width(150.dp)



        ) {
            Text("Bank",
                color = CustomColors.ShadowedWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            onClick = {
                viewModel.getCurrentLocation()
                viewModel.destination = Destination.Garten
            },
            colors = ButtonDefaults.buttonColors(containerColor = ButtonBlue),
            modifier = Modifier
                .width(150.dp)

        ) {
            Text("Garten",
                color = CustomColors.ShadowedWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}