package com.weatherApp.core.domain.model

data class Weather(
        val id: Int = 0,
        val time: String = "",
        val temperature: Double = 0.0,
        val locationKey: String = ""
    )
