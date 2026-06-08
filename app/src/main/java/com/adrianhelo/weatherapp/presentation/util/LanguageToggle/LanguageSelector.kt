package com.adrianhelo.weatherapp.presentation.util.LanguageToggle

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
fun LanguageSelector(
    selectedLanguage: String,
    onUnitClick: () -> Unit)
{
    // Verificación explícita
    val isEn = selectedLanguage == "en"

    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.2f)) // Fondo oscuro translúcido
            .clickable { onUnitClick() }
            .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        // Opción Ingles
        UnitItem(text = "EN", isSelected = isEn)

        // Opción Español
        UnitItem(text = "SP", isSelected = !isEn)
    }
}