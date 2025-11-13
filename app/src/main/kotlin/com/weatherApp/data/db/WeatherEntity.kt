package com.weatherApp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey




@Entity(tableName = "weather_data")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val time: String,
    val temperature: Double
)
