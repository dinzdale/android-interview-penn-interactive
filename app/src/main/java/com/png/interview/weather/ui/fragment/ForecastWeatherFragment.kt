package com.png.interview.weather.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.png.interview.R
import com.png.interview.databinding.FragmentForecastWeatherBinding
import com.png.interview.ui.InjectedFragment
import com.png.interview.weather.ui.binder.ForecastWeatherFragmentViewBinder
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel

class ForecastWeatherFragment :  InjectedFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewModel = getViewModel<CurrentWeatherViewModel>()
        // Inflate the layout for this fragment
        return  FragmentForecastWeatherBinding.inflate(inflater, container, false).apply{
            viewBinder = ForecastWeatherFragmentViewBinder(viewModel,sharedPreferences)
        }.root
    }

}