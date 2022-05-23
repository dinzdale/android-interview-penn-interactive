package com.png.interview.weather.ui.viewmodel

import androidx.lifecycle.*
import com.png.interview.weather.api.AutoCompleteRepository
import com.png.interview.weather.api.ForecastRepository
import com.png.interview.weather.api.WeatherApi
import com.png.interview.weather.api.model.AutcompleteResponseItem
import com.png.interview.weather.api.model.Forecastday
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
    val enteredLocation = _enteredLocation.debounce(500).asLiveData()
    var lastLocation  = MutableStateFlow<String?>(null)

    private val _currentWeatherViewRepresentation = MutableStateFlow<CurrentWeatherViewRepresentation>(CurrentWeatherViewRepresentation.Empty)
    private val _threeDayForecastResult = MutableStateFlow<CurrentWeatherViewRepresentation>(CurrentWeatherViewRepresentation.Empty)

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


    fun getAutoCompleteList(query:String) : LiveData<List<AutcompleteResponseItem>>{
        val result = MutableLiveData<List<AutcompleteResponseItem>>()
        viewModelScope.launch {
            autoCompleteRepository.getAutocompleteResults(query).also { response->
                when {
                    response is CurrentWeatherViewRepresentation.AutoCompleteRep ->{
                        result.value = response.data
                    }
                    //TODO handle network error
                }
            }
        }
        return result
    }

    fun getThreeDayForecast() {
        lastLocation.value?.also {
            viewModelScope.launch {
                _threeDayForecastResult.value = forecastRepository.getForecast(it)
                val result = _threeDayForecastResult.value
                val list = when(result) {
                    is CurrentWeatherViewRepresentation.ForecastWeatherViewRep->{
                        result.data.forecastday.take(3).toCollection(mutableListOf())
                    }
                    else -> emptyList<Forecastday>()
                }
                _forecasts.value = list
            }
        }
    }

    val isForecastVisible = _threeDayForecastResult
    .map{ it is CurrentWeatherViewRepresentation.ForecastWeatherViewRep }.asLiveData()

    private val _forecasts = MutableStateFlow<List<Forecastday>>(emptyList())
    val forecasts = _forecasts.asLiveData()
}