package com.geeksforgeeks.demo.models

data class BottomNavItem(
    // Text below icon
    val label: String,
    // Icon
    val icon: Int,
    // Route to the specific screen
    val route:String,
)