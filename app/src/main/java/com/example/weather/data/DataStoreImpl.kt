package com.example.weather.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weather.di.AppModule
import com.example.weather.domain.DataStore
import com.example.weather.domain.WeatherResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = AppModule.DATA_STORE)

class DataStoreImpl @Inject constructor(private val context: Context) : DataStore {

    private val weatherResponseKey = stringPreferencesKey("weather_response_data")

    override suspend fun saveData(weatherResponse: WeatherResponse) {
        val json = Gson().toJson(weatherResponse)
        context.dataStore.edit { preferences ->
            preferences[weatherResponseKey] = json
        }
    }

    override val weatherResponseFlow: Flow<WeatherResponse?> =
        context.dataStore.data.map { preferences ->
            preferences[weatherResponseKey]?.let {
                Gson().fromJson(it, WeatherResponse::class.java)
            }
        }


}