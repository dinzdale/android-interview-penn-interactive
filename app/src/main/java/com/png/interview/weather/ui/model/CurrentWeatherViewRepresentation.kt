package com.png.interview.weather.ui.model

import com.png.interview.weather.api.model.AutoCompleteResponse
import com.png.interview.weather.api.model.Forecast

sealed class CurrentWeatherViewRepresentation {
    class AvailableWeatherViewRep(val data: AvailableWeatherViewData) : CurrentWeatherViewRepresentation()
    class AutoCompleteRep(val data: AutoCompleteResponse) : CurrentWeatherViewRepresentation()
    class ForecastWeatherViewRep(val data: Forecast) : CurrentWeatherViewRepresentation()
    object Empty : CurrentWeatherViewRepresentation()
    object Error : CurrentWeatherViewRepresentation()
}
