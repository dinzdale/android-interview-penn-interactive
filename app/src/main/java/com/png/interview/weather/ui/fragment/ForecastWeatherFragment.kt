package com.png.interview.weather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.png.interview.databinding.FragmentForecastWeatherBinding
import com.png.interview.ui.InjectedFragment
import com.png.interview.weather.api.model.Forecastday
import com.png.interview.weather.ui.binder.ForecastWeatherFragmentViewBinder
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel
import kotlinx.android.synthetic.main.forecast_item.view.*
import kotlinx.android.synthetic.main.fragment_forecast_weather.view.*

class ForecastWeatherFragment : InjectedFragment() {
    lateinit var binder: FragmentForecastWeatherBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewModel = getViewModel<CurrentWeatherViewModel>()
        // Inflate the layout for this fragment
        binder = FragmentForecastWeatherBinding.inflate(inflater, container, false).apply {
            viewBinder = ForecastWeatherFragmentViewBinder(viewModel)
        }

        return binder.root
    }

    override fun onResume() {
        super.onResume()
        if (::binder.isInitialized) {
            binder.viewBinder?.forecasts?.observe(viewLifecycleOwner) {
                it.forEachIndexed { index, forecastday ->
                    when (index) {
                        0 -> {
                           bindDays(binder.root.day_1,forecastday)
                        }
                        1 -> {
                            bindDays(binder.root.day_2,forecastday)
                        }
                        2 -> {
                            bindDays(binder.root.day_3,forecastday)
                        }
                    }
                }
            }
        }
    }


    private fun bindDays(container: View, forecastDay: Forecastday) {
        val isMetric = sharedPreferences.getBoolean("metric", false)
        container.date_value.text = forecastDay.date
        container.min_tmp_value.text = if (isMetric.not()) {
            "${forecastDay.day.mintemp_f.toString()}F"
        }
        else {
            "${forecastDay.day.mintemp_c.toString()}C"
        }
        container.max_tmp_value.text = if (isMetric.not()) {
            "${forecastDay.day.maxtemp_f.toString()}F"
        }
        else {
            "${forecastDay.day.maxtemp_c.toString()}C"
        }
        container.wind_speed_value.text = if (isMetric.not()) {
            "${forecastDay.day.maxwind_mph.toString()} MPH"
        }
        else {
            "${forecastDay.day.maxwind_mph.toString()} KMH"
        }
        container.condition_value.text = forecastDay.day.condition.text
    }
}