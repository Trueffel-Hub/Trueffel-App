package com.geeksforgeeks.demo.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
            icon = R.drawable.compass,
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