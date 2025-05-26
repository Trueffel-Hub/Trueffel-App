package com.example.trueffel_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trueffel_app.repository.ToastViewModel
import com.geeksforgeeks.demo.utils.CustomColors

@Composable
fun ToastScreen(model: ToastViewModel) {
    var displayedText by remember { mutableStateOf(model.currentToast) }
    var toasts_left by remember { mutableStateOf(model.toastLeft) }
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.StandardGrey)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )

        {
            Text(
                text = "Übrige Trinksprüche: $toasts_left",
                color = CustomColors.ShadowedWhite,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    Text(
                        text = displayedText,
                        color = CustomColors.ShadowedWhite,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        model.currentToast = model.getRandomToast().toString()
                        displayedText = model.currentToast
                        toasts_left = model.toastLeft
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89B7F5))
                ) {
                    Text("Neuer Toast")
                }

                Spacer(modifier = Modifier.width(50.dp))

                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89B7F5))
                ) {
                    Text("Reset")
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Bestätigung") },
                text = { Text("Alle Toasts zurücksetzten?") },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Nein")
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        model.resetToasts()
                        toasts_left = 34 //TODO
                        displayedText = model.currentToast
                        showDialog = false
                    }) {
                        Text("Ja")
                    }
                }
            )
        }
    }
}