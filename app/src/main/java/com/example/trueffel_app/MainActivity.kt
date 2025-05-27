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
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.trueffel_app.ui.theme.DemoTheme
import com.example.trueffel_app.ui.viewmodel.ToiletteViewModel
import com.geeksforgeeks.demo.utils.Constants
import com.geeksforgeeks.demo.utils.CustomColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var compassViewModel: CompassViewModel
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null

    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null

    private var azimuth by mutableStateOf(0f)
    private var updateJob: Job? = null
    var rotation by mutableStateOf(0f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val toastViewModel = ViewModelProvider(this)[ToastViewModel::class.java]
        val compassViewModel = ViewModelProvider(this)[CompassViewModel::class.java]
        val toiletteViewModel = ViewModelProvider(this)[ToiletteViewModel::class.java]

        sensorManager = ContextCompat.getSystemService(this, SensorManager::class.java)!!
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        lifecycleScope.launch {
            compassViewModel.bearing.collect {
                while (true) {
                    rotation = if (azimuth < 180) {
                        (it - (azimuth + 180) + 360) % 360
                    } else {
                        (it - (azimuth - 180) + 360) % 360

                    }
                        delay(1000)
                }
            }
        }

        setContent {

            val navController = rememberNavController()
            Surface(color = Color.Red) {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }, content = { padding ->
                        NavHostContainer(
                            navController = navController,
                            padding = padding,
                            toastModel = toastViewModel,
                            compassViewModel = compassViewModel,
                            toiletteViewModel = toiletteViewModel,
                            context = this,
                            rotation = rotation,
                            azimuth = azimuth
                        )
                    }
                )
            }

        }

    }


    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }
        magnetometer?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }
        startAzimuthUpdates()
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        updateJob?.cancel()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> gravity = event.values
            Sensor.TYPE_MAGNETIC_FIELD -> geomagnetic = event.values
        }
    }

    private fun startAzimuthUpdates() {
        updateJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(100)
                updateAzimuth()
            }
        }
    }

    private fun updateAzimuth() {
        if (gravity != null && geomagnetic != null) {
            val rotationMatrix = FloatArray(9)
            val inclinationMatrix = FloatArray(9)
            if (SensorManager.getRotationMatrix(
                    rotationMatrix,
                    inclinationMatrix,
                    gravity,
                    geomagnetic
                )
            ) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientation)
                azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
    toastModel: ToastViewModel,
    compassViewModel: CompassViewModel,
    toiletteViewModel: ToiletteViewModel,
    context: Context,
    rotation: Float,
    azimuth: Float
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable("home") {
                HomeScreen()
            }
            composable("toilette") {
                ToiletteScreen(toiletteViewModel)
            }
            composable("compass") {
                CompassScreen(compassViewModel, rotation, azimuth)
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
                    selectedTextColor = CustomColors.ShadowedWhite,
                    indicatorColor = CustomColors.ShadowedWhite,
                    selectedIconColor = CustomColors.CommentGray,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}

