package com.png.interview.weather.api

import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.ui.model.CurrentWeatherViewRepresentation
import javax.inject.Inject

class AutoCompleteRepository  (private val weatherApi: WeatherApi) {
    suspend fun getAutocompleteResults(query:String)  = weatherApi.getAutocompleteResults(query).let { response->
            when {
                response is NetworkResponse.Success->{
                    CurrentWeatherViewRepresentation.AutoCompleteRep(response.body)
                }
                else -> CurrentWeatherViewRepresentation.Error
            }
        }

}