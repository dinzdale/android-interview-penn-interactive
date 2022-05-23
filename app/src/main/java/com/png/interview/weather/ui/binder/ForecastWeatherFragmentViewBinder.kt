package com.png.interview.weather.ui.binder

import android.content.SharedPreferences
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel

class ForecastWeatherFragmentViewBinder(
    private val viewModel: CurrentWeatherViewModel,
    private val sharedPreferences: SharedPreferences,
) {
    init {
        viewModel.getThreeDayForecast()
    }

}