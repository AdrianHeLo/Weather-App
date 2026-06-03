package com.adrianhelo.weatherapp.presentation.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.adrianhelo.weather.R
import com.adrianhelo.weatherapp.domain.FutureModel
import com.adrianhelo.weatherapp.domain.HourlyModel
import com.adrianhelo.weatherapp.presentation.ui.MainActivity.MainViewModel
import com.adrianhelo.weatherapp.presentation.util.FutureItem
import com.adrianhelo.weatherapp.presentation.util.FutureModelViewHolder
import com.adrianhelo.weatherapp.presentation.util.WeatherDetail
import com.adrianhelo.weatherapp.presentation.util.WeatherIcon

// --- Sample Data ---
val hourlyItems = listOf(
    HourlyModel("9 PM", 24, "cloudy"),
    HourlyModel("10 PM", 23, "sunny"),
    HourlyModel("11 PM", 22, "windy"),
    HourlyModel("12 AM", 21, "cloudy_sunny"),
    HourlyModel("1 AM", 20, "storm")
)

val dailyItems = listOf(
    FutureModel("Tue", "storm", "Storm", 24, 12),
    FutureModel("Wed", "cloudy", "Cloudy", 25, 16),
    FutureModel("Thu", "sunny", "Sunny", 28, 19),
    FutureModel("Fri", "rainy", "Rainy", 23, 15),
    FutureModel("Sat", "windy", "Windy", 22, 13),
    FutureModel("Sun", "cloudy_sunny", "Cloudy Sunny", 26, 16)
)

@Composable
fun WeatherContent(viewModel: MainViewModel) {
    // Observamos el flujo de datos. 'state' se actualizará automáticamente.
    val weatherResponse by viewModel.weatherData.collectAsStateWithLifecycle()

    // Background Gradient Box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF59469D), Color(0xFF643D67))
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Current Weather Header
            item {
                Text(
                    text = weatherResponse?.name ?: "Loading",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 48.dp)
                )
            }

            // 2. Main Weather Icon
            item {
                val iconImage = weatherResponse?.weather?.firstOrNull()?.icon
                WeatherIcon(iconImage)
            }

            // 3. Weather Description
            item {
                Text(
                    text = "Description: ${weatherResponse?.weather?.firstOrNull()?.description ?: "Loading"}",
                    fontSize = 19.sp,
                    color = Color.White
                )
            }

            // 4. Temperature
            item {
                Row (
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Text(
                        text = "${weatherResponse?.main?.temp}°",
                        fontSize = 63.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "C",
                        fontSize = 63.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // 5. High/Low Temp
            item {
                Row {
                    Text(
                        text = "H: ${weatherResponse?.main?.tempMax}°",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = "L: ${weatherResponse?.main?.tempMin}°",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            // 6. Weather Details Box
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .background(
                            color = colorResource(id = R.color.purple),
                            shape = RoundedCornerShape(25.dp)
                        )
                        .padding(vertical = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        WeatherDetail(R.drawable.rain, "22", "%","Rain")
                        WeatherDetail(R.drawable.wind, "${weatherResponse?.wind?.speed}", "km/h","Wind Speed")
                        WeatherDetail(R.drawable.humidity, "${weatherResponse?.main?.humidity}", "%", "Humidity")
                    }
                }
            }

            // 7. Today Label
            item {
                Text(
                    text = "Today",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 24.dp, bottom = 12.dp),
                    textAlign = TextAlign.Start
                )
            }

            // 8. Hourly Forecast Row
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(hourlyItems) { item ->
                        FutureModelViewHolder(item)
                    }
                }
            }

            // 9. Future Label
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Future",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Next 7 days >",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // 10. Daily Forecast List
            items(dailyItems) { item ->
                FutureItem(item)
            }
        }
    }
}