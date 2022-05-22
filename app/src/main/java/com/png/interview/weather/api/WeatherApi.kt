package com.png.interview.weather.api

import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.api.model.AutcompleteResponseItem
import com.png.interview.weather.api.model.AutoCompleteResponse
import com.png.interview.weather.api.model.CurrentWeatherResponse
import com.png.interview.weather.api.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("current.json")
    suspend fun getCurrentWeather(@Query("q") query: String): NetworkResponse<CurrentWeatherResponse, Unit>

    @GET("search.json")
    suspend fun getAutocompleteResults(@Query("q") query: String): NetworkResponse<List<AutcompleteResponseItem>, Unit>

    @GET("forecast.json")
    suspend fun getForecast(@Query("q") query: String, @Query("days") days: Int = 10): NetworkResponse<ForecastResponse, Unit>
}