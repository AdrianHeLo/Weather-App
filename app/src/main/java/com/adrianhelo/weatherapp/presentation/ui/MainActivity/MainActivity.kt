package com.adrianhelo.weatherapp.presentation.ui.MainActivity

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.adrianhelo.weather.R
import com.adrianhelo.weatherapp.presentation.theme.WeatherAppTheme
import com.adrianhelo.weatherapp.presentation.ui.Screen.WeatherScreen
import com.adrianhelo.weatherapp.presentation.util.UnitsToggle.UnitSelector
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var lang: String = "en"

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
            // 1. Obtenemos la ubicación del usuario
            val lat = intent.getDoubleExtra("LATITUDE", 0.0)
            val lon = intent.getDoubleExtra("LONGITUDE", 0.0)

            // 2. Recolectamos el estado de las unidades desde DataStore
            val selectedUnit by mainViewModel.unitsState.collectAsStateWithLifecycle()

            if (lat != 0.0 && lon != 0.0) {
                // Usar la ubicación recibida
                Log.i("MAIN_ACTIVITY", "Latitude is $lat")
                Log.i("MAIN_ACTIVITY", "Longitude is $lon")

                // 3. Cada vez que 'units' cambie, se dispara la API automáticamente
                LaunchedEffect(selectedUnit) {
                    // Reemplaza con tus coordenadas reales
                    mainViewModel.getWeather(
                        lat = lat,
                        lon = lon,
                        units = selectedUnit, // Usamos el valor actualizado
                        lang = lang,
                        apiKey = getString(R.string.api_key)
                    )
                }

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
                                label = {
                                    Icon(
                                        painterResource(R.drawable.ic_back),
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp).rotate(180f)
                                    )
                                        },
                                selected = false,
                                onClick = { scope.launch { drawerState.close() } }
                            )
                            NavigationDrawerItem(
                                label = {
                                    Row (horizontalArrangement = Arrangement.Center){
                                        Text(
                                            text = "Units",
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                            fontSize = 16.sp
                                        )

                                        Spacer(modifier = Modifier.padding(10.dp))

                                        // Aquí invocamos nuestro componente personalizado
                                        UnitSelector(
                                            // Pasamos 'units' (el valor actual) y la función toggle
                                            selectedUnit = selectedUnit,
                                            onUnitClick = { mainViewModel.toggleUnits() }
                                        )
                                    }
                                },
                                selected = false,
                                onClick = {}
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
        }
    }

}
