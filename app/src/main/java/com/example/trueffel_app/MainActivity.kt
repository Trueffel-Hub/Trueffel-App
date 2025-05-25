package com.example.trueffel_app



import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.trueffel_app.repository.ToastViewModel
import com.example.trueffel_app.ui.screens.HomeScreen
import com.example.trueffel_app.ui.screens.CompassScreen
import com.example.trueffel_app.ui.screens.ToastScreen
import com.example.trueffel_app.ui.theme.DemoTheme
import com.geeksforgeeks.demo.utils.Constants

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var toastViewModel = ViewModelProvider(this)[ToastViewModel::class.java]

        setContent {
            DemoTheme(dynamicColor = false, darkTheme = true) {
                val navController = rememberNavController()
                Surface(color = Color.Red) {
                    // Scaffold Component
                    Scaffold(
                        // Bottom navigation
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }, content = { padding ->
                            // Nav host: where screens are placed
                            NavHostContainer(navController = navController, padding = padding, toastModel = toastViewModel)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    toastModel: ToastViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable("home") {
                HomeScreen()
            }
            composable("compass") {
                CompassScreen()
            }
            composable("toast") {


                ToastScreen(toastModel)
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    NavigationBar(

        // set background color for navigation bar
        containerColor = Color(0xFFbcbcbc)) {


        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        Constants.BottomNavItems.forEach { navItem ->

            // Place the bottom nav items
            NavigationBarItem(

                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,

                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },

                // Icon of navItem
                icon = {

                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = navItem.label,
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified
                    )
                },

                // label
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = true,

                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = Color.White, // Label color when selected
                    indicatorColor = Color(0xffbcbcbc) // Highlight color for selected item
                )
            )
        }
    }
}

