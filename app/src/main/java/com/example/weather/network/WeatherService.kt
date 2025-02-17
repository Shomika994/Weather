package com.example.weather.network

import com.example.weather.models.WeatherResponse
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

interface WeatherService {
    @GET("2.5/weather")
   suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String?,
        @Query("appId") appId: String?
    ): Call<WeatherResponse>
}