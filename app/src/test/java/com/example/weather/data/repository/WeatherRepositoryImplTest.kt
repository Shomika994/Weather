package com.example.weather.data.repository

import com.example.weather.data.network.WeatherResponse
import com.example.weather.data.network.WeatherService
import com.example.weather.domain.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var repository: WeatherRepositoryImpl
    private lateinit var weatherService: WeatherService

    @Before
    fun setUp() {
        weatherService = mockk()
        repository = WeatherRepositoryImpl(weatherService)
    }

    @Test
    fun `fetchWeather returns Success when service call is successful`() = runTest {
        val lat = 10.0
        val lon = 20.0
        val weatherResponse = WeatherResponse(
            base = "stations",
            clouds = WeatherResponse.Clouds(all = 0),
            cod = 200,
            coord = WeatherResponse.Coord(lat = lat, lon = lon),
            dt = 1630000000,
            id = 123,
            main = WeatherResponse.Main(
                feelsLike = 25.0,
                grndLevel = 1000,
                humidity = 50,
                pressure = 1013,
                seaLevel = 1013,
                temp = 25.0,
                tempMax = 28.0,
                tempMin = 22.0
            ),
            name = "Test City",
            rain = null,
            sys = WeatherResponse.Sys(
                country = "US",
                id = 123,
                sunrise = 1630000000,
                sunset = 1630050000,
                type = 1
            ),
            timezone = 0,
            weather = listOf(
                WeatherResponse.Weather(
                    description = "clear sky",
                    icon = "01d",
                    id = 800,
                    main = "Clear"
                )
            ),
            wind = WeatherResponse.Wind(
                deg = 0,
                gust = 0.0,
                speed = 5.0
            )
        )
        
        coEvery { 
            weatherService.getWeather(lat, lon, any(), any()) 
        } returns weatherResponse


        val result = repository.fetchWeather(lat, lon)


        assertTrue(result is Resource.Success)
        val weatherData = (result as Resource.Success).data
        assertEquals("Test City", weatherData?.name)
        assertEquals(25.0, weatherData?.temperature)
    }

    @Test
    fun `fetchWeather returns Error when service call throws exception`() = runTest {

        val lat = 10.0
        val lon = 20.0
        val errorMessage = "Network error"
        
        coEvery { 
            weatherService.getWeather(lat, lon, any(), any()) 
        } throws RuntimeException(errorMessage)

        val result = repository.fetchWeather(lat, lon)

        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)
    }
}
