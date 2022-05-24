package com.png.interview.weather.api

import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.ui.model.NetworkResponseViewRepresentation

class AutoCompleteRepository  (private val weatherApi: WeatherApi) {
    suspend fun getAutocompleteResults(query:String)  = weatherApi.getAutocompleteResults(query).let { response->
            when {
                response is NetworkResponse.Success->{
                    NetworkResponseViewRepresentation.AutoCompleteRep(response.body)
                }
                else -> NetworkResponseViewRepresentation.Error
            }
        }

}