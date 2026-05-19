package com.adrianhelo.weatherapp.presentation.util.UnitsToggle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UnitItem(text: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(if (isSelected) Color.White else Color.Transparent) // El círculo blanco
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}