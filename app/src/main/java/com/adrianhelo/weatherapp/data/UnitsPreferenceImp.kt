package com.adrianhelo.weatherapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class UnitsPreferenceImp @Inject constructor(private val context: Context){

    private val UNITS_KEY = stringPreferencesKey("units_preference")

    // Leer las unidades (por defecto "metric")
    val unitsFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[UNITS_KEY] ?: "metric"
    }

    // Guardar las unidades
    suspend fun saveUnits(units: String) {
        context.dataStore.edit { preferences ->
            preferences[UNITS_KEY] = units
        }
    }
}