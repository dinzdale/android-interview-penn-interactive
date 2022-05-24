package com.png.interview.weather.ui.model

import com.png.interview.weather.api.model.AutcompleteResponseItem
import com.png.interview.weather.api.model.Forecast

sealed class NetworkResponseViewRepresentation {
    class AvailableWeatherViewRep(val data: AvailableWeatherViewData) : NetworkResponseViewRepresentation()
    class AutoCompleteRep(val data: List<AutcompleteResponseItem>) : NetworkResponseViewRepresentation()
    class ForecastWeatherViewRep(val data: Forecast) : NetworkResponseViewRepresentation()
    object Empty : NetworkResponseViewRepresentation()
    object Error : NetworkResponseViewRepresentation()
}
