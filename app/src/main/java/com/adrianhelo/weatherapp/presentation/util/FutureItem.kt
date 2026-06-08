package com.adrianhelo.weatherapp.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adrianhelo.weatherapp.domain.models.FutureModel

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