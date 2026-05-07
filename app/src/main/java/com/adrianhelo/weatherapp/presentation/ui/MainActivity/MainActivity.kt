package com.adrianhelo.weatherapp.presentation.ui.MainActivity

import android.content.res.Resources.Theme
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultScaleX
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.adrianhelo.weather.R
import com.adrianhelo.weatherapp.domain.FutureModel
import com.adrianhelo.weatherapp.domain.HourlyModel
import com.adrianhelo.weatherapp.domain.Weather
import com.adrianhelo.weatherapp.presentation.theme.WeatherAppTheme
import com.adrianhelo.weatherapp.presentation.ui.Screen.WeatherScreen
import com.adrianhelo.weatherapp.presentation.util.FutureItem
import com.adrianhelo.weatherapp.presentation.util.FutureModelViewHolder
import com.adrianhelo.weatherapp.presentation.util.WeatherDetail
import com.adrianhelo.weatherapp.presentation.util.WeatherIcon
import com.adrianhelo.weatherapp.presentation.util.getDrawableResourceId
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var units: String = ""
    private var lang: String = ""

    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set up edge-to-edge with no layout limits
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContent {
            WeatherAppTheme{
                // 1. Estado para controlar el Drawer
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                // 2. El componente principal del Drawer
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    // Contenido del menú lateral
                    drawerContent = {
                        ModalDrawerSheet {
                            Spacer(modifier = Modifier.height(12.dp))
                            NavigationDrawerItem(
                                label = { Text("Settings") },
                                selected = false,
                                onClick = { scope.launch { drawerState.close() } }
                            )
                            NavigationDrawerItem(
                                label = { Text("Hello") },
                                selected = false,
                                onClick = {scope.launch { drawerState.isClosed }}
                            )
                        }
                    }
                ) {
                    // 3. Contenido principal de la App
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(getString(R.string.app_name)) },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {scope.launch { drawerState.open() }
                                    }) {
                                        Icon(painterResource(R.drawable.ic_menu), contentDescription = "Menu")
                                    }
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Magenta,
                                    titleContentColor = Color.White,
                                )
                            )
                        }
                    ) { padding ->
                        // Usa 'padding' para que el contenido no se tape con la TopBar
                        Box(modifier = Modifier.padding(padding)) {
                            WeatherScreen(mainViewModel)
                        }
                    }
                }
            }
        }

        val lat = intent.getDoubleExtra("LATITUDE", 0.0)
        val lon = intent.getDoubleExtra("LONGITUDE", 0.0)
        if (lat != 0.0 && lon != 0.0) {
            // Usar la ubicación recibida
            Log.i("MAIN_ACTIVITY", "Latitude is $lat")
            Log.i("MAIN_ACTIVITY", "Longitude is $lon")
            units = "metric"
            lang = "en"

            mainViewModel.viewModelScope.launch {
                mainViewModel.getWeather(lat, lon, units, lang, getString(R.string.api_key))
            }
        }

    }
}
