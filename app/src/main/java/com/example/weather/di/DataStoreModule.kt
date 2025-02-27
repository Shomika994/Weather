package com.example.weather.di

import android.content.Context
import com.example.weather.data.DataStoreImpl
import com.example.weather.domain.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun bindDataStore(@ApplicationContext context: Context): DataStore {
        return DataStoreImpl(context)
    }
}