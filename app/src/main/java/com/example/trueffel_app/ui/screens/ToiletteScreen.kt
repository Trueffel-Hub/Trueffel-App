import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trueffel_app.ui.viewmodel.Profile
import com.example.trueffel_app.ui.viewmodel.ToiletteViewModel
import com.geeksforgeeks.demo.utils.CustomColors
import com.geeksforgeeks.demo.utils.CustomColors.ButtonBlue
import com.geeksforgeeks.demo.utils.CustomColors.ColumnColor1
import com.geeksforgeeks.demo.utils.CustomColors.ColumnColor2
import com.geeksforgeeks.demo.utils.CustomColors.ShadowedWhite


@Composable
fun ToiletteScreen(viewModel: ToiletteViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var showDialogWinner by remember { mutableStateOf(false) }
    var highestProfiles by remember { mutableStateOf<List<Profile>>(emptyList()) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.StandardGrey),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = "Toilette Counter",
                color = CustomColors.ShadowedWhite,
                fontSize = 35.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
            LazyColumn {
                itemsIndexed(viewModel.profiles) { index, profile ->
                    ProfileRow(profile, index) { clickedProfile ->
                        viewModel.profiles[index] =
                            clickedProfile.copy(counter = clickedProfile.counter + 1)
                    }
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
                            showDialogWinner = true
                            highestProfiles = getPlayersWithHighestCounter(viewModel.profiles)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonBlue),
                        modifier = Modifier
                            .width(150.dp)
                    ) {
                        Text(
                            "Result",
                            color = CustomColors.ButtonTextColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Button(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonBlue),
                        modifier = Modifier
                            .width(150.dp)

                    ) {
                        Text(
                            "Reset",
                            color = CustomColors.ButtonTextColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
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
    val backgroundColor = if (index % 2 == 0) ColumnColor1 else ColumnColor2


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(profile) }
            .background(backgroundColor)
            .height(50.dp)
            .padding(10.dp),


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
        Text(
            text = profile.name,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = ShadowedWhite
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = profile.counter.toString(),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = ShadowedWhite
        )
    }
}


fun getPlayersWithHighestCounter(profiles: List<Profile>): List<Profile> {
    val maxCounter = profiles.maxOfOrNull { it.counter } ?: return emptyList()
    return profiles.filter { it.counter == maxCounter }
}