package com.example.trueffel_app



import android.content.Context
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.trueffel_app.repository.ToastViewModel
import com.example.trueffel_app.ui.viewmodel.CompassViewModel
import com.example.trueffel_app.ui.screens.HomeScreen
import com.example.trueffel_app.ui.screens.CompassScreen
import com.example.trueffel_app.ui.screens.ToastScreen
import ToiletteScreen
import com.example.trueffel_app.ui.theme.DemoTheme
import com.example.trueffel_app.ui.viewmodel.ToiletteViewModel
import com.geeksforgeeks.demo.utils.Constants
import com.geeksforgeeks.demo.utils.CustomColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val toastViewModel = ViewModelProvider(this)[ToastViewModel::class.java]
        val compassViewModel = ViewModelProvider(this)[CompassViewModel::class.java]
        val toiletteViewModel = ViewModelProvider(this)[ToiletteViewModel::class.java]

        setContent {

                val navController = rememberNavController()
                Surface(color = Color.Red) {
                    // Scaffold Component
                    Scaffold(
                        // Bottom navigation
                        bottomBar = {
                            BottomNavigationBar(navController = navController)
                        }, content = { padding ->
                            // Nav host: where screens are placed
                            NavHostContainer(
                                navController = navController,
                                padding = padding,
                                toastModel = toastViewModel,
                                compassViewModel=compassViewModel,
                                toiletteViewModel=toiletteViewModel,
                                context = this)
                        }
                    )
                }

        }

    }
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    toastModel: ToastViewModel,
    compassViewModel: CompassViewModel,
    toiletteViewModel: ToiletteViewModel,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable("home") {
                HomeScreen()
            }
            composable("toilette"){
                ToiletteScreen(toiletteViewModel)
            }
            composable("compass") {
                CompassScreen(compassViewModel)
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
        containerColor = Color(0xFF2A2E31)
    ) {


        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        Constants.BottomNavItems.forEach { navItem ->

            NavigationBarItem(

                selected = currentRoute == navItem.route,

                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = navItem.label,
                        modifier = Modifier.size(40.dp),

                    )
                },
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = true,

                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = CustomColors.ShadowedWhite, // Label color when selected
                    indicatorColor = CustomColors.ShadowedWhite,
                    selectedIconColor = CustomColors.CommentGray,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}

