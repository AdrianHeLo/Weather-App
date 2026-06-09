package com.adrianhelo.weatherapp.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adrianhelo.weather.R
import com.adrianhelo.weatherapp.domain.models.HourlyModel
import com.adrianhelo.weatherapp.presentation.util.WeatherIcons.getDrawableResourceId

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