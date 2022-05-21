package com.png.interview.weather.ui.viewmodel

import androidx.lifecycle.*
import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.api.AutoCompleteRepository
import com.png.interview.weather.api.ForecastRepository
import com.png.interview.weather.api.WeatherApi
import com.png.interview.weather.api.model.AutoCompleteResponse
import com.png.interview.weather.api.model.ForecastResponse
import com.png.interview.weather.domain.CreateCurrentWeatherRepFromQueryUseCase
import com.png.interview.weather.ui.model.CurrentWeatherViewRepresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(
    private val createCurrentWeatherRepFromQueryUseCase: CreateCurrentWeatherRepFromQueryUseCase,
    private val weatherApi: WeatherApi
) : ViewModel() {
    private val forecastRepository = ForecastRepository(weatherApi)
    private val autoCompleteRepository = AutoCompleteRepository(weatherApi)

    private val _enteredLocation = MutableStateFlow<String?>(null)
    val enteredLocation = _enteredLocation.debounce(1000).asLiveData()

    private val _currentWeatherViewRepresentation = MutableStateFlow<CurrentWeatherViewRepresentation>(CurrentWeatherViewRepresentation.Empty)
    private val _threeDayForecast = MutableStateFlow<CurrentWeatherViewRepresentation>(CurrentWeatherViewRepresentation.Empty)

    fun updateLocationEntry(text: String) {
        _enteredLocation.value = text
    }
    fun submitCurrentWeatherSearch(query: String) {
        viewModelScope.launch {
            _currentWeatherViewRepresentation.value = createCurrentWeatherRepFromQueryUseCase(query)
        }
    }


    val availableCurrentWeatherLiveData =
        _currentWeatherViewRepresentation
            .map { (it as? CurrentWeatherViewRepresentation.AvailableWeatherViewRep)?.data }
            .asLiveData()

    val isEmptyVisible =
        _currentWeatherViewRepresentation
            .map { it is CurrentWeatherViewRepresentation.Empty }
            .asLiveData()

    val isErrorVisible =
        _currentWeatherViewRepresentation
            .map { it is CurrentWeatherViewRepresentation.Error }
            .asLiveData()


    fun getAutoCompleteList(query:String) : LiveData<AutoCompleteResponse>{
        val result = MutableLiveData<AutoCompleteResponse>()
        viewModelScope.launch {
            autoCompleteRepository.getAutocompleteResults(query).also { response->
                when {
                    response is CurrentWeatherViewRepresentation.AutoCompleteRep ->{
                        result.value = response.data
                    }
                }
            }
        }
        return result
    }

    fun getThreeDayForecast(query: String) {
        viewModelScope.launch {
            _threeDayForecast.value = forecastRepository.getForecast(query)
        }
    }

    val threeDayForeCast = _threeDayForecast.asLiveData()

}