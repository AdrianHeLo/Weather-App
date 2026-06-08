package com.adrianhelo.weatherapp.domain

import com.adrianhelo.weatherapp.data.UserPreference
import com.adrianhelo.weatherapp.data.repository.ImperialUnitFactory
import com.adrianhelo.weatherapp.data.repository.MetricUnitFactory
import com.adrianhelo.weatherapp.domain.repository.UnitFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UnitProvider @Inject constructor(private val userPreference: UserPreference) {
    val currentUnityFactory: Flow<UnitFactory> = userPreference.settingsFlow.map { settings ->

        val temperatureSymbol =  settings.first

        when(temperatureSymbol){
            "metric" -> MetricUnitFactory()
            "imperial" -> ImperialUnitFactory()
            else -> MetricUnitFactory()
        }
    }
}