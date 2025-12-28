package com.weatherApp.core.domain.repository

import com.weatherApp.core.data.remote.api.WeatherApiService
import com.weatherApp.core.data.local.db.WeatherDao
import com.weatherApp.core.data.remote.firebase.FirebaseService
import com.weatherApp.core.domain.model.Weather
import com.weatherApp.core.domain.datasource.PreferencesManager
import com.weatherApp.core.data.mapper.toDomain
import com.weatherApp.core.data.mapper.toEntity

//TODO incluir la referencia a la BBDD remota (Firebase)
class WeatherRepositoryImpl(
    private val dao: WeatherDao,
    private val api: WeatherApiService,
    private val prefs: PreferencesManager,
    private val firebase: FirebaseService

):WeatherRepository {

    override fun getSavedLocation(): Triple<String?, Float?, Float?> {
        val name = prefs.getCityName()
        val latitude = prefs.getLatitude()
        val longitude = prefs.getLongitude()
        return Triple(name,latitude,longitude)
    }

    override fun saveLocation(name: String,latitude: Double,longitude: Double) {
        prefs.saveLocation(name,latitude,longitude)
    }

    override suspend fun fetchWeather() {
        val savedLocation = getSavedLocation()
        val latitude = savedLocation.second
        val longitude = savedLocation.third

        if(latitude == null || longitude == null){
            return
        }

        val response = api.getWeather(latitude, longitude)
        val temperatures = response.hourly.temperature_2m.mapIndexed { index, temp ->
            Weather(
                id = 0,
                temperature = temp,
                time = response.hourly.time[index]
            )
        }
        dao.clearDatabase()
        dao.insertWeatherData(temperatures.map{it.toEntity()})
        syncWithFirebase(temperatures)
    }

    override suspend fun getTodaysWeather():List<Weather> {
        return dao.retrieveWeatherData().map{it.toDomain()}
    }

    /**
     * TODO obtiene todos los registros de la BBDD
     */
    override suspend fun getHistoricWeather(): List<Weather> {
        return firebase.getWeatherData()
    }

    /**
     * TODO añade solo los elementos cuya clave no exista ya en la BBDD remota
     */
    private suspend fun syncWithFirebase(data: List<Weather>) {
        val existingKeys = firebase.getExistingWeatherIds()
        data.forEach { weather ->
            if(!existingKeys.contains(weather.time)){
                firebase.addWeatherData(weather)
            }
        }
    }
}

