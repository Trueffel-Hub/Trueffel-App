package com.geeksforgeeks.demo.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.geeksforgeeks.demo.models.BottomNavItem
import com.example.trueffel_app.R

object Constants {
    val BottomNavItems = listOf(
        // Home screen
        BottomNavItem(
            label = "Home",
            icon = R.drawable.home,
            route = "home"
        ),
        BottomNavItem(
            label = "Toilette",
            icon = R.drawable.toilet,
            route = "toilette"
        ),
        // Search screen
        BottomNavItem(
            label = "Compass",
            icon = R.drawable.compass2,
            route = "compass"
        ),
        // Profile screen
        BottomNavItem(
            label = "Toast",
            icon = R.drawable.toast2,
            route = "toast"
        ),


    )
}


object CustomColors{
    @Stable
    val StandardGrey = Color(0xFF222222)

    @Stable
    val CommentGray = Color(0xFF2A2E31)

    @Stable
    val ShadowedWhite = Color(0xFFF2F5F4)

    @Stable
    val ButtonBlue = Color(0xFF7FC7FF)

}