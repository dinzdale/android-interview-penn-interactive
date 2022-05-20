package com.png.interview.weather.ui.model

sealed class CurrentWeatherViewRepresentation {
    class AvailableWeatherViewRep(val data: AvailableWeatherViewData) : CurrentWeatherViewRepresentation()
    object Empty : CurrentWeatherViewRepresentation()
    object Error : CurrentWeatherViewRepresentation()
}
