package com.adrianhelo.weatherapp.presentation.util

import androidx.compose.runtime.Composable

@Composable
fun setCapitalizedText(text: String?): String{
    var textContent = ""
    if (text != null){
        textContent = if (text.isNotEmpty()){
            text.replaceFirstChar { it.uppercaseChar() }
        }else{
            text
        }
    }
    return textContent
}