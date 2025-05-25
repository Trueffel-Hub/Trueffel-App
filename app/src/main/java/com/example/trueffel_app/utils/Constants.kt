package com.geeksforgeeks.demo.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.geeksforgeeks.demo.models.BottomNavItem

object Constants {
    val BottomNavItems = listOf(
        // Home screen
        BottomNavItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = "home"
        ),
        // Search screen
        BottomNavItem(
            label = "Search",
            icon = Icons.Filled.Search,
            route = "search"
        ),
        // Profile screen
        BottomNavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = "profile"
        )
    )
}