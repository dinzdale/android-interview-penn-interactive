package com.png.interview.weather.ui.binder

import com.png.interview.weather.ui.viewmodel.WeatherViewModel

class ForecastWeatherFragmentViewBinder(
    private val viewModel: WeatherViewModel,
) {
    init {
        viewModel.getThreeDayForecast()
    }

    val isForecast = viewModel.isForecastVisible

    val forecasts = viewModel.forecasts


}