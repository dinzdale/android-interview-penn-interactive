package com.png.interview.weather.ui.binder

import android.content.SharedPreferences
import android.view.View
import android.widget.RadioButton
import com.png.interview.R


class SettingsFragementViewBinder(val sharedPreferences: SharedPreferences)
{

    fun isMetric() = sharedPreferences.getBoolean("metric",false)

    fun onClick(view: View) {
        val btnId = (view as RadioButton).id
        when(btnId) {
            R.id.imperial_btn->{sharedPreferences.edit().putBoolean("metric",false).commit()}
            R.id.metric_btn->{sharedPreferences.edit().putBoolean("metric",true).commit()}
        }
    }
}