package com.lychee.ui.main.map

import android.arch.lifecycle.ViewModel
import com.lychee.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MapFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(mapViewModel: MapViewModel) : ViewModel
}