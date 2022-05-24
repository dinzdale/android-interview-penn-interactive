package com.png.interview.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.png.interview.dagger.viewmodel.ViewModelFactory
import com.png.interview.dagger.viewmodel.ViewModelKey
import com.png.interview.weather.ui.viewmodel.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    internal abstract fun currentWeatherViewModel(viewModel: WeatherViewModel): ViewModel
}
