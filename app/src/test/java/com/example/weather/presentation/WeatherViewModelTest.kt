package com.example.weather.presentation

import android.location.Location
import com.example.weather.data.FakeDataStore
import com.example.weather.data.FakeLocation
import com.example.weather.data.FakeRepository
import com.example.weather.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    private val mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeRepository
    private lateinit var fakeLocation: FakeLocation
    private lateinit var fakeDataStore: FakeDataStore
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        val location = mockk<Location> {
            every { latitude } returns 44.20
            every { longitude } returns 20.00
        }
        fakeRepository = FakeRepository(false)
        fakeLocation = FakeLocation(location)
        fakeDataStore = FakeDataStore()
        viewModel = WeatherViewModel(fakeRepository,
                                         fakeLocation, fakeDataStore)
    }

    @Test
    fun loadDataShouldFetchLocationSuccessfully() = runTest(mainDispatcherRule.testDispatcher) {
        viewModel.loadData()
        advanceUntilIdle()

        assertNotNull(viewModel.state.weatherData)
        assertNull(viewModel.state.error)
    }

    @Test
    fun weatherRepoReturnsError() = runTest(mainDispatcherRule.testDispatcher) {
        val repo = FakeRepository(true)
        viewModel = WeatherViewModel(repo, fakeLocation, fakeDataStore)

        viewModel.loadData()
        advanceUntilIdle()

        assertNotNull(viewModel.state.error)
        assertNull(viewModel.state.weatherData)
    }

    @Test
    fun locationIsNull() = runTest(mainDispatcherRule.testDispatcher) {

        val location = FakeLocation(null)

        viewModel = WeatherViewModel(fakeRepository,
                                         location, fakeDataStore)
        viewModel.loadData()
        advanceUntilIdle()

        assertNull(viewModel.state.weatherData)
        assertNotNull(viewModel.state.error)
    }

    @Test
    fun shouldSaveWeatherData() = runTest(mainDispatcherRule.testDispatcher) {
        viewModel.loadData()
        advanceUntilIdle()

        val savedData = fakeDataStore.weatherDataFlow.dropWhile { it == null }.first()

        assertNotNull(savedData)
        assertEquals(viewModel.state.weatherData, savedData)
    }


}