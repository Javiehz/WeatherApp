package com.weatherApp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    val sharedPrefs =
        context.getSharedPreferences("sharedPreferencesFile", Context.MODE_PRIVATE)

    fun saveLocation(city: String, latitude: Double, longitude: Double) {
        with(sharedPrefs.edit()) {
            putString("city", city)
            putFloat("latitude", latitude.toFloat())
            putFloat("longitude", longitude.toFloat())
            apply()
        }
    }


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
