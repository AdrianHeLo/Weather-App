package com.adrianhelo.weatherapp.presentation.ui.SplashActivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adrianhelo.weather.R
import com.adrianhelo.weatherapp.presentation.ui.MainActivity.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    // 1. Definimos el lanzador para el pop-up de permisos
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            // El usuario aceptó: obtenemos ubicación y luego navegamos
            getLastLocationAndNavigate()
        } else {
            // El usuario rechazó: navegamos directo a la siguiente actividad
            navigateToMain()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set up edge-to-edge with no layout limits
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContent {
            SplashScreen()
        }
        checkLocationPermissions()
    }

    private fun checkLocationPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocationAndNavigate() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // Aquí tienes la latitud y longitud
                    val lat = location.latitude
                    val lon = location.longitude
                }
                navigateToMain(location)
            }
            .addOnFailureListener {
                navigateToMain()
            }
    }

    private fun navigateToMain(location: Location? = null) {
        val intent = Intent(this, MainActivity::class.java).apply {
            location?.let {
                putExtra("LATITUDE", it.latitude)
                putExtra("LONGITUDE", it.longitude)
            }
        }
        startActivity(intent)
        finish() // Cerramos la Splash para que no puedan volver atrás
    }

}

