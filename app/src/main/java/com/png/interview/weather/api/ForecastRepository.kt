package com.png.interview.weather.api

import com.png.interview.api.common_model.NetworkResponse
import javax.inject.Inject

class ForecastRepository @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun getForecast(query:String)  = weatherApi.getForecast(query,3)
}