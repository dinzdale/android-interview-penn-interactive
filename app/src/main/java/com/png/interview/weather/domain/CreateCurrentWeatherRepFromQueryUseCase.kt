package com.png.interview.weather.domain

import android.content.SharedPreferences
import com.png.interview.api.common_model.NetworkResponse
import com.png.interview.weather.ui.model.AvailableWeatherViewData
import com.png.interview.weather.ui.model.NetworkResponseViewRepresentation
import javax.inject.Inject

interface CreateCurrentWeatherRepFromQueryUseCase {
    suspend operator fun invoke(query: String): NetworkResponseViewRepresentation
}

class DefaultCreateCurrentWeatherRepFromQueryUseCase @Inject constructor(
    private val getCurrentWeatherDataUseCase: GetCurrentWeatherDataUseCase,
    private val sharedPreferences: SharedPreferences
) : CreateCurrentWeatherRepFromQueryUseCase {
    override suspend fun invoke(query: String): NetworkResponseViewRepresentation {
        return when (val result = getCurrentWeatherDataUseCase(query)) {
            is NetworkResponse.Success -> {
                val isMetric = sharedPreferences.getBoolean("metric", false)
                NetworkResponseViewRepresentation.AvailableWeatherViewRep(
                    AvailableWeatherViewData(
                        name = result.body.location.name,
                        date = result.body.location.localtime,
                        temperature = if (isMetric.not()) {
                            "${result.body.current.temp_f} F"
                        }
                        else {
                            "${result.body.current.temp_c} C"
                        },
                        condition = result.body.current.condition.text,
                        windDirection = result.body.current.wind_dir,
                        windSpeed = if (isMetric.not()) {
                            "${result.body.current.gust_mph} MPH"
                        }
                        else {
                            "${result.body.current.gust_kph} KPH"
                        }
                    )
                )
            }
            else -> {
                NetworkResponseViewRepresentation.Error
            }
        }
    }
}