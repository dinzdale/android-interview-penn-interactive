package com.png.interview.weather.ui.binder
import android.app.Activity
import android.widget.Toast
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel


class CurrentWeatherFragmentViewBinder(
    private val viewModel: CurrentWeatherViewModel,
    private val activity: Activity,
    private val settingsAction: () -> Unit,
    private val forecastAction: () -> Unit,
) {

    val availableWeatherViewData = viewModel.availableCurrentWeatherLiveData
    val isEmpty = viewModel.isEmptyVisible
    val isError = viewModel.isErrorVisible

    val input = viewModel.enteredLocation


    /**
     * Re-use go locking for refresh
     */
//    fun refreshClicked() {
//        //Toast.makeText(activity, "Refresh Clicked TODO", Toast.LENGTH_LONG).show()
//    }

    fun seeForecastClicked() {
        forecastAction()
    }

    fun settingsClicked() {
        settingsAction()
    }

    fun resetAndSaveEnteredLocation(location:String?=viewModel.enteredLocation.value, clearField: Boolean = true) {
        location?.also {
            viewModel.lastLocation.value = it
        }
        if (clearField) {
            viewModel.updateLocationEntry("")
        }
    }

    fun goClicked() {
        input.value?.also {
            if (it.isEmpty()) {
                Toast.makeText(activity, "Please Enter Query", Toast.LENGTH_LONG).show()
            }
            else if (it.length < 3) {
                Toast.makeText(activity, "Please Enter More than 3 Characters", Toast.LENGTH_LONG)
                    .show()
            }
            else {
                resetAndSaveEnteredLocation(clearField = false)
                viewModel.submitCurrentWeatherSearch(it)
            }
        }
    }
    fun refreshClicked() {
        viewModel.lastLocation.value?.also {
            viewModel.submitCurrentWeatherSearch(it)
        }
    }


    fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
        viewModel.updateLocationEntry(text.toString())
    }
}