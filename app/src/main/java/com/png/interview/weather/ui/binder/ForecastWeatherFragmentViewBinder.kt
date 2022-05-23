package com.png.interview.weather.ui.binder

import android.content.SharedPreferences
import android.view.View
import android.widget.TextView
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel

class ForecastWeatherFragmentViewBinder(
    private val viewModel: CurrentWeatherViewModel,
) {
    init {
        viewModel.getThreeDayForecast()
    }

    val isForecast = viewModel.isForecastVisible

    val forecasts = viewModel.forecasts


}