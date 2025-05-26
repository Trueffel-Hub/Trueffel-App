import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trueffel_app.R
import com.example.trueffel_app.ui.viewmodel.Profile
import com.example.trueffel_app.ui.viewmodel.ToiletteViewModel


@Composable
fun ToiletteScreen(viewModel: ToiletteViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var showDialogWinner by remember { mutableStateOf(false) }
    var highestProfiles by remember { mutableStateOf<List<Profile>>(emptyList()) }


    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Toilette Counter",
            textAlign = TextAlign.Center,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(23.dp)
        )
        LazyColumn {
            itemsIndexed(viewModel.profiles) { index, profile ->
                ProfileRow(profile, index) { clickedProfile ->
                    println("Clicked: ${clickedProfile.name}")
                    viewModel.profiles[index] =
                        clickedProfile.copy(counter = clickedProfile.counter + 1)
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89B7F5)),
                modifier = Modifier
                    .width(300.dp)
                    .padding(vertical = 15.dp)
            ) { Text("Reset All") }

            Button(
                onClick = {
                    showDialogWinner = true
                    highestProfiles = getPlayersWithHighestCounter(viewModel.profiles)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89B7F5)),
                modifier = Modifier
                    .width(300.dp)
                    .padding(vertical = 5.dp)
            ) {
                Text("Wer musste am Öftesten")
            }
        }

        if (showDialogWinner) {
            AlertDialog(
                onDismissRequest = { showDialogWinner = false },
                title = { Text("Ergebnisse") },
                text = {
                    Column {
                        highestProfiles.forEach { profile ->
                            Text("${profile.name} war ${profile.counter}-mal am Baum.")
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { showDialogWinner = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89B7F5))
                    ) {
                        Text("OK")
                    }
                }
            )
        }






        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Bestätigung") },
                text = { Text("Alle Counter zurücksetzten?") },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Nein")
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.profiles.replaceAll({ it.copy(counter = 0) })
                        showDialog = false
                    }) {
                        Text("Ja")
                    }
                }
            )
        }


    }
}


@Composable
fun ProfileRow(profile: Profile, index: Int, onClick: (Profile) -> Unit) {
    val backgroundColor = if (index % 2 == 0) Color.LightGray else Color.Gray


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(profile) }
            .background(backgroundColor)
            .padding(3.dp)
            .height(60.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = profile.imageResId),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(40.dp),
            alpha = 0.3f
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = profile.name, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = profile.counter.toString(), fontSize = 25.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ShowHighestProfileDialog(profiles: List<Profile>, onDismiss: () -> Unit) {
    val highestProfile = profiles.maxByOrNull { it.counter }

    if (highestProfile != null) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Highest Counter") },
            text = { Text("${highestProfile.name} war ${highestProfile.counter} am Baum.") },
            confirmButton = {
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89B7F5))
                ) {
                    Text("Ok")
                }
            }
        )
    }
}

fun getPlayersWithHighestCounter(profiles: List<Profile>): List<Profile> {
    val maxCounter = profiles.maxOfOrNull { it.counter } ?: return emptyList()
    return profiles.filter { it.counter == maxCounter }
}