package com.adrianhelo.weatherapp.presentation.ui.SplashActivity

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
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(){

    /*
    LaunchedEffect(Unit) {
        delay(2000)
        onFinish()
    }
    */

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF59469D), Color(0xFF643D67))
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Image(
                painter = painterResource(id = R.drawable.weather_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )

            Text(
                text = "Fetching the currently weather...",
                fontSize = 20.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(150.dp))

            Image(
                painter = painterResource(id = R.drawable.openweather),
                contentDescription = "Open Weather",
                modifier = Modifier.size(100.dp)
            )

            Text(
                text = "Powered by Open Weather API.",
                fontSize = 15.sp,
                color = Color.White
            )

        }
    }
}