package com.png.interview.weather.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.png.interview.weather.api.WeatherApi
import javax.inject.Inject

class ForecastWeatherViewModel @Inject constructor(val weatherApi: WeatherApi) : ViewModel() {

}