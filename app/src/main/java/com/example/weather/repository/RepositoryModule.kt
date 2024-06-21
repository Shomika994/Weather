package com.example.weather.repository

import android.app.Application
import com.example.weather.network.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherService: WeatherService,
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherService)
    }
}