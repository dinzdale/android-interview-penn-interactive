package com.png.interview.weather.ui.model

sealed class CurrentWeatherViewRepresentation {
    class AvailableWeatherViewRep(val data: AvailableWeatherViewData) : CurrentWeatherViewRepresentation()
    object Empty : CurrentWeatherViewRepresentation()
    class Error(val data: String = "Canned Error Message") : CurrentWeatherViewRepresentation()
}
