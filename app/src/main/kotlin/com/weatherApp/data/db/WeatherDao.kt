package com.weatherApp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query




@Dao
interface WeatherDao {

    @Insert
    suspend fun insertWeatherData(data: List<WeatherEntity>)

    @Query("SELECT * FROM weather_data ORDER BY time ASC")
    fun retrieveWeatherData(): LiveData<List<WeatherEntity>>

    @Query("DELETE FROM weather_data")
    suspend fun clearDatabase()
}
