package com.weatherApp.core.data.remote.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.weatherApp.core.domain.model.Weather
import kotlinx.coroutines.tasks.await

class FirebaseService {

    //TODO definir referencia e instancia de BBDD

    private val database: DatabaseReference =
        FirebaseDatabase.getInstance("https://weatherappfirebase-a11ec-default-rtdb.europe-west1.firebasedatabase.app/")
            .reference
            .child("weather")

    /**
     * TODO obtiene todos los registros almacenados en la BBDD, ordenados por fecha y hora
     * Devuelve un listado de Weather
     */
    suspend fun getWeatherData(locationKey: String): List<Weather> {
        val snapshot = database.child(locationKey).get().await()
        val list = snapshot.children.mapNotNull { it.getValue(Weather::class.java) }
        return list.sortedBy { it.time }
    }

    /**
     * TODO obtiene todos los IDs (fecha/hora) de los registros almacenados en la BBDD
     * No es necesario implementarla si no la necesitáis en vuestra solución
     */
    suspend fun getExistingWeatherIds(locationKey: String): Set<String> {
        val snapshot = database.child(locationKey).get().await()
        return snapshot.children.mapNotNull { it.key }.toSet()
    }


    /**
     * TODO añade un nuevo registro a la BBDD
     * La clave del registro debe ser la fecha y hora
     */
    suspend fun addWeatherData(weatherData: Weather) {
        val key = weatherData.time
        database
            .child(weatherData.locationKey)
            .child(key)
            .setValue(weatherData)
            .await()
    }
}
