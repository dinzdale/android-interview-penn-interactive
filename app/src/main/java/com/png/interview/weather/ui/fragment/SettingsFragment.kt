package com.png.interview.weather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.png.interview.databinding.FragmentSettingsBinding
import com.png.interview.ui.InjectedFragment
import com.png.interview.weather.ui.binder.SettingsFragementViewBinder
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel

class SettingsFragment : InjectedFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewModel = getViewModel<CurrentWeatherViewModel>()
        val binding = FragmentSettingsBinding.inflate(inflater, container, false).apply {
            viewBinder = SettingsFragementViewBinder(sharedPreferences)
        }
        return binding.root
    }
}