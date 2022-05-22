package com.png.interview.weather.ui.binder

import android.app.Activity
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel
import timber.log.Timber
import javax.inject.Inject

class SettingsFragementViewBinder(val sharedPreferences: SharedPreferences)
{
    init {
        sharedPreferences?.apply {
            Timber.d("Got shared preferences")
        }
    }

    fun isMetric() = sharedPreferences.getBoolean("metric",false)




}