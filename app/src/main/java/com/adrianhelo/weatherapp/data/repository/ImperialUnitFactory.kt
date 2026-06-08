package com.adrianhelo.weatherapp.data.repository

import com.adrianhelo.weatherapp.domain.repository.UnitFactory

class ImperialUnitFactory: UnitFactory {
    override val temperatureSymbol: String = "°F"
    //override val windSpeedSymbol: String = "mph"
}