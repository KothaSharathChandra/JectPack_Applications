package com.example.jetweatherforecast.di

import android.content.Context
import androidx.room.Room
import com.example.jetweatherforecast.data.WeatherDao
import com.example.jetweatherforecast.data.WeatherDataBase
import com.example.jetweatherforecast.network.WeatherApi
import com.example.jetweatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDataBase: WeatherDataBase): WeatherDao
    =weatherDataBase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDataBase
    = Room.databaseBuilder(
        context,
         WeatherDataBase::class.java,
        "weather_database")
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}