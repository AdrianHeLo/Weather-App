package com.adrianhelo.weatherapp.data.repository

import com.adrianhelo.weatherapp.domain.repository.UnitFactory

class MetricUnitFactory: UnitFactory{

    override val temperatureSymbol: String = "°C"
    override val windSpeedSymbol: String = "km/h"

}