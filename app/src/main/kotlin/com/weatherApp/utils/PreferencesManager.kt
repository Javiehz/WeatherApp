package com.weatherApp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    //TODO get sharedPreferences file
    val sharedPrefs =
        context.getSharedPreferences("sharedPreferencesFile", Context.MODE_PRIVATE)

    //TODO save data in SharedPreferences
    fun saveLocation(city: String, latitude: Double, longitude: Double) {
        with(sharedPrefs.edit()) {
            putString("city", city)
            putFloat("latitude", latitude.toFloat())
            putFloat("longitude", longitude.toFloat())
            apply()
        }
    }
    //TODO implement getters for SharedPreferences values, using mock data by now

    fun getCityName(): String? {
        return sharedPrefs.getString("city", "No Hay")
    }
    fun getLatitude(): Float? {
        return sharedPrefs.getFloat("latitude", 0.0f)
    }
    fun getLongitude(): Float? {
        return sharedPrefs.getFloat("longitude", 0.0f)
    }
}
