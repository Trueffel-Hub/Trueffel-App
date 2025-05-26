package com.example.trueffel_app.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.trueffel_app.R

data class Profile(
    val imageResId: Int,
    val name: String,
    var counter: Int
)

class ToiletteViewModel : ViewModel() {

    val profiles = mutableStateListOf(
        Profile(R.drawable.toilet, "Kai", 0),
        Profile(R.drawable.toilet, "Lennart", 0),
        Profile(R.drawable.toilet, "Tim", 0),
        Profile(R.drawable.toilet, "Sven", 0),
        Profile(R.drawable.toilet, "Hendrik", 0),
        Profile(R.drawable.toilet, "Johannes", 0),
        Profile(R.drawable.toilet, "Basti", 0),
        Profile(R.drawable.toilet, "Marvin", 0),
    )

}