package com.example.trueffel_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.trueffel_app.R


@Composable
fun ToastScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon on the screen
        Image(
            painter = painterResource(id = R.drawable.toast2),
            contentDescription = "Custom Toast Icon",
            modifier = Modifier.size(64.dp) // Größe anpassen
        )
        // Text on the screen
        Text(text = "Toast", color = Color.Black)
    }
}