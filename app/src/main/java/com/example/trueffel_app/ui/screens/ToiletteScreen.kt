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

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Toilette Counter",
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth().padding(23.dp)
        )
        LazyColumn {
            itemsIndexed(viewModel.profiles) { index, profile ->
                ProfileRow(profile, index) { clickedProfile ->
                    println("Clicked: ${clickedProfile.name}")
                    viewModel.profiles[index] = clickedProfile.copy(counter = clickedProfile.counter + 1)
                }
            }
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
            .height(70.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = profile.imageResId),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = profile.name, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = profile.counter.toString(), fontSize = 25.sp, fontWeight = FontWeight.Bold)
    }
}

