package com.png.interview.weather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.fragment.findNavController
import com.png.interview.databinding.FragmentCurrentWeatherBinding
import com.png.interview.ui.InjectedFragment
import com.png.interview.weather.api.model.AutoCompleteResponse
import com.png.interview.weather.ui.binder.CurrentWeatherFragmentViewBinder
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel
import kotlinx.android.synthetic.main.activity_main.mainNavigationFragment
import timber.log.Timber

class CurrentWeatherFragment : InjectedFragment() {

    val autoCompleteResponseMediator =  MediatorLiveData<AutoCompleteResponse>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewModel = getViewModel<CurrentWeatherViewModel>()
        val view =  FragmentCurrentWeatherBinding.inflate(inflater, container,false).apply {
            viewBinder = CurrentWeatherFragmentViewBinder(
                viewModel,
                requireActivity(),
                settingsAction = {
                    findNavController().navigate(CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToSettingsFragment())
                },
                forecastAction = {
                    findNavController().navigate(CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToForecastWeatherFragment())
                }
            )
            this.lifecycleOwner = viewLifecycleOwner
        }.root

        autoCompleteResponseMediator.addSource(viewModel.enteredLocation) { enteredText->
            enteredText?.also {
                autoCompleteResponseMediator.addSource(viewModel.getAutoCompleteList(enteredText)) {
                    autoCompleteResponseMediator.value = it
                }
            }
        }
        autoCompleteResponseMediator.observe(viewLifecycleOwner) {
            it.forEach {
                Timber.d(" autocomplete: ${it.name}")
            }
        }

        return view
    }
}