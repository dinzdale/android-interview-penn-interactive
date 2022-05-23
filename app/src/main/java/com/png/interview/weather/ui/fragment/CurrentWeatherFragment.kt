package com.png.interview.weather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.fragment.findNavController
import com.png.interview.databinding.FragmentCurrentWeatherBinding
import com.png.interview.ui.InjectedFragment
import com.png.interview.weather.api.model.AutcompleteResponseItem
import com.png.interview.weather.ui.binder.CurrentWeatherFragmentViewBinder
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel

class CurrentWeatherFragment : InjectedFragment() {

    val autoCompleteResponseMediator =  MediatorLiveData<List<AutcompleteResponseItem>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewModel = getViewModel<CurrentWeatherViewModel>()
        val binding =  FragmentCurrentWeatherBinding.inflate(inflater, container,false).apply {
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
        }

        autoCompleteResponseMediator.removeSource(viewModel.enteredLocation)
        autoCompleteResponseMediator.addSource(viewModel.enteredLocation) { enteredText->
            enteredText?.also {
                if (it.length >= 3) {
                    autoCompleteResponseMediator.addSource(viewModel.getAutoCompleteList(enteredText)) {
                        autoCompleteResponseMediator.value = it
                    }
                }
            }
        }
        autoCompleteResponseMediator.observe(viewLifecycleOwner) {
            val results = it.map { it.name }.take(5)
            val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1)
            adapter.addAll(results)
            binding.etInput.setAdapter(adapter)
            binding.etInput.showDropDown()
        }

        binding.etInput.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            binding.viewBinder?.resetAndSaveEnteredLocation(binding.etInput.editableText.toString())
            autoCompleteResponseMediator.value = emptyList()
            binding.etInput.dismissDropDown()
            viewModel.submitCurrentWeatherSearch((view as AppCompatTextView).text.toString())
        }
        return binding.root
    }
}