package com.adrianhelo.weatherapp.presentation.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adrianhelo.weather.R
import com.adrianhelo.weatherapp.domain.FutureModel
import com.adrianhelo.weatherapp.domain.HourlyModel
import dagger.hilt.android.AndroidEntryPoint

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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Set up edge-to-edge with no layout limits
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            WeatherScreen()
        }
    }
}

@Composable
fun WeatherScreen() {
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
                    text = "Mostly Cloudy",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 48.dp)
                )
            }

            // 2. Main Weather Icon
            item {
                Image(
                    painter = painterResource(id = R.drawable.cloudy_sunny),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp).padding(top = 8.dp)
                )
            }

            // 3. Date & Time
            item {
                Text(
                    text = "Mon June 17 | 10:00 AM",
                    fontSize = 19.sp,
                    color = Color.White
                )
            }

            // 4. Temperature
            item {
                Text(
                    text = "25°",
                    fontSize = 63.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // 5. High/Low Temp
            item {
                Text(
                    text = "H:27 L:18",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
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
                        WeatherDetail(R.drawable.rain, "22%", "Rain")
                        WeatherDetail(R.drawable.wind, "12km/h", "Wind Speed")
                        WeatherDetail(R.drawable.humidity, "18%", "Humidity")
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

// --- Helper Components ---

@Composable
fun WeatherDetail(icon: Int, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(34.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Composable
fun FutureModelViewHolder(model: HourlyModel) {
    Column(
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.purple),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = model.hour, color = Color.White)
        Image(
            painter = painterResource(id = getDrawableResourceId(model.picPath)),
            contentDescription = null,
            modifier = Modifier
                .size(45.dp)
                .padding(vertical = 8.dp)
        )
        Text(text = "${model.temp}°", color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun FutureItem(item: FutureModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.day,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.width(50.dp)
        )
        Image(
            painter = painterResource(id = getDrawableResourceId(item.picPath)),
            contentDescription = null,
            modifier = Modifier.size(45.dp)
        )
        Text(
            text = item.status,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        Text(
            text = "${item.highTemp}°",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "${item.lowTemp}°",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.alpha(0.7f) // Slight fade for low temp
        )
    }
}

@Composable
fun getDrawableResourceId(picPath: String): Int {
    return when (picPath) {
        "storm" -> R.drawable.storm
        "cloudy" -> R.drawable.cloudy
        "sunny" -> R.drawable.sunny
        "rainy" -> R.drawable.rainy
        "windy" -> R.drawable.windy
        "cloudy_sunny" -> R.drawable.cloudy_sunny
        else -> R.drawable.sunny
    }
}