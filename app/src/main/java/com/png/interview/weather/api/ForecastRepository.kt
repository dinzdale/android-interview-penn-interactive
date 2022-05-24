package com.png.interview.weather.api

import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.ui.model.NetworkResponseViewRepresentation

class ForecastRepository  (private val weatherApi: WeatherApi) {
    suspend fun getForecast(query:String)  = weatherApi.getForecast(query,3).let { response->
            when {
                response is NetworkResponse.Success->{
                    NetworkResponseViewRepresentation.ForecastWeatherViewRep(response.body.forecast)
                }
                else -> NetworkResponseViewRepresentation.Error
            }
        }
}