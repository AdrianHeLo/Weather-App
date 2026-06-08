package com.adrianhelo.weatherapp.presentation.util.UnitsToggle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.adrianhelo.weatherapp.presentation.util.UnitItem

@Composable
fun UnitSelector(
    selectedUnit: String,
    onUnitClick: () -> Unit
) {
    // Verificación explícita
    val isMetric = selectedUnit == "metric"

    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.2f)) // Fondo oscuro translúcido
            .clickable { onUnitClick() }
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Opción Celsius
        UnitItem(text = "°C", isSelected = isMetric)

        // Opción Fahrenheit
        UnitItem(text = "°F", isSelected = !isMetric)
    }
}
